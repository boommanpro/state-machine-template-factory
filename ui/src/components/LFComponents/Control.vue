<template>
  <div>
    <el-button-group>
      <!--      <el-button type="default" size="small" @click="stateListTable = true">查看状态列表</el-button>-->
      <!--      <el-button type="default" size="small" @click="">查看事件列表</el-button>-->
      <el-button type="default" size="small" @click="$_zoomIn">放大</el-button>
      <el-button type="default" size="small" @click="$_zoomOut">缩小</el-button>
      <el-button type="default" size="small" @click="$_zoomReset">大小适应</el-button>
      <el-button type="default" size="small" @click="$_translateRest">定位还原</el-button>
      <el-button type="default" size="small" @click="$_reset">还原(大小&定位)</el-button>
      <el-button type="default" size="small" @click="$_undo" :disabled="undoDisable">上一步(ctrl+z)</el-button>
      <el-button type="default" size="small" @click="$_redo" :disabled="redoDisable">下一步(ctrl+y)</el-button>
      <el-button type="default" size="small" @click="$_download">下载图片</el-button>
      <el-button type="default" size="small" @click="$_catData">查看原始数据</el-button>
      <el-button type="default" size="small" @click="$catStateMachineData">查看状态机数据</el-button>
      <el-button type="default" size="small" @click="$catStateEventData">查看状态事件数据</el-button>
      <el-button type="default" size="small" @click="$_showMiniMap">查看缩略图</el-button>
    </el-button-group>


    <el-dialog v-model="stateListTable" title="状态列表" width="800">
      <StateManagement :state-list="stateList"/>
    </el-dialog>

  </div>
</template>
<script>
import StateManagement from "../statemachine/StateManagement.vue";

export default {
  name: 'Control',
  components: {StateManagement},
  props: {
    lf: Object || String,
    stateList: Array,
    eventList: Array,
  },
  data() {
    return {
      undoDisable: true,
      redoDisable: true,
      graphData: null,
      dataVisible: false,
      stateListTable: false
    }
  },
  mounted() {
    this.$props.lf.on('history:change', ({data: {undoAble, redoAble}}) => {
      this.$data.undoDisable = !undoAble
      this.$data.redoDisable = !redoAble
    });
  },
  methods: {
    $_zoomIn() {
      this.$props.lf.zoom(true);
    },
    $_zoomOut() {
      this.$props.lf.zoom(false);
    },
    $_zoomReset() {
      this.$props.lf.resetZoom();
    },
    $_translateRest() {
      this.$props.lf.resetTranslate();
    },
    $_reset() {
      this.$props.lf.resetZoom();
      this.$props.lf.resetTranslate();
    },
    $_undo() {
      this.$props.lf.undo();
    },
    $_redo() {
      this.$props.lf.redo();
    },
    $_download() {
      this.$props.lf.getSnapshot();
    },
    $_catData() {
      this.$emit('catData');
    },
    $catStateMachineData() {
      this.$emit('catStateMachineData');
    },
    $catStateEventData() {
      this.$emit('catStateEventData');
    },
    $_showMiniMap() {
      const {lf} = this.$props;
      lf.extension.miniMap.show(lf.graphModel.width - 150, 40)
    }
  }
}
</script>
<style scoped>
</style>
