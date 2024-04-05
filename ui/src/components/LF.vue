<template>
  <div class="logic-flow-view">
    <h3 class="demo-title">状态机维护</h3>
    <!-- 辅助工具栏 -->
    <Control
        class="demo-control"
        v-if="lf"
        :lf="lf"
        :catTurboData="true"
        @catData="$_catData"
        @catStateMachineData="$_catStateMachineData"
        @catStateEventData="$_catStateEventData"
    ></Control>
    <!-- 节点面板 -->
    <NodePanel v-if="lf" :lf="lf" :nodeList="nodeList"></NodePanel>

    <!-- 画布 -->
    <div id="LF-view" ref="container"></div>
    <!-- 用户节点自定义操作面板 -->

    <!-- 属性面板 -->
    <RightCollapse
        title="属性面板"
        :initialWidth="400"
        v-if="dialogVisible"
        @closePanel="closeDialog"
    >
      <PropertyDialog
          v-if="dialogVisible"
          :nodeData="clickNode"
          :lf="lf"
          @setPropertiesFinish="closeDialog"
      ></PropertyDialog>
    </RightCollapse>


    <!-- 数据查看面板 -->
    <el-dialog
        title="数据"
        v-model="dataVisible"
        width="50%">
      <DataDialog :graphData="graphData"></DataDialog>
    </el-dialog>
  </div>
</template>
<script>
import LogicFlow from '@logicflow/core'
import {Menu, MiniMap, Snapshot} from '@logicflow/extension'
import '@logicflow/core/dist/style/index.css'
import '@logicflow/extension/lib/style/index.css'
import NodePanel from './LFComponents/NodePanel.vue'
import Control from './LFComponents/Control.vue'
import PropertyDialog from './PropertySetting/PropertyDialog.vue'
import DataDialog from './LFComponents/DataDialog.vue'
import {nodeList} from './config'

import {
  registerConnect,
  registerDownload,
  registerEnd,
  registerPolyline,
  registerPush,
  registerStart,
  registerTask,
  registerUser,
} from './registerNode'
import demoData from './data.json';
import RightCollapse from "./collapse/RightCollapse.vue";

export default {
  name: 'LF',
  components: {NodePanel, Control, PropertyDialog, DataDialog, RightCollapse},
  data() {
    return {
      lf: null,
      showAddPanel: false,
      addPanelStyle: {
        top: 0,
        left: 0
      },
      nodeData: null,
      addClickNode: null,
      clickNode: null,
      dialogVisible: false,
      graphData: null,
      dataVisible: false,
      config: {
        background: {
          backgroundColor: '#f7f9ff',
        },
        grid: {
          size: 10,
          visible: false
        },
        keyboard: {
          enabled: true
        },
        edgeTextDraggable: true,
        hoverOutline: false,
      },
      moveData: {},
      nodeList,
    }
  },
  mounted() {
    this.$_initLf()
  },
  methods: {
    $_initLf() {
      // 画布配置
      const lf = new LogicFlow({
        ...this.config,
        plugins: [
          Menu,
          MiniMap,
          Snapshot
        ],
        container: this.$refs.container,
      })
      this.lf = lf
      // 设置主题
      lf.setTheme({
        circle: {
          stroke: '#000000',
          strokeWidth: 1,
          outlineColor: '#88f',
        },
        rect: {
          outlineColor: '#88f',
          strokeWidth: 1
        },
        polygon: {
          strokeWidth: 1
        },
        polyline: {
          stroke: '#000000',
          hoverStroke: '#000000',
          selectedStroke: '#000000',
          outlineColor: '#88f',
          strokeWidth: 1
        },
        nodeText: {
          color: '#000000'
        },
        edgeText: {
          color: '#000000',
          background: {
            fill: '#f7f9ff'
          }
        },
      })
      this.$_registerNode()
    },
    // 自定义
    $_registerNode() {
      registerStart(this.lf)
      registerUser(this.lf)
      registerEnd(this.lf)
      registerPush(this.lf, this.clickPlus, this.mouseDownPlus)
      registerDownload(this.lf)
      registerPolyline(this.lf)
      registerTask(this.lf)
      registerConnect(this.lf)
      this.$_render()
    },
    $_render() {
      this.lf.render(demoData)
      this.$_LfEvent()
    },
    $_getData() {
      const data = this.lf.getGraphData()
      console.log(JSON.stringify(data))
    },
    $_LfEvent() {
      this.lf.on('node:click', ({data}) => {
        console.log('node:click', data)
        this.$data.clickNode = data
        this.$data.dialogVisible = true
      })
      this.lf.on('edge:click', ({data}) => {
        console.log('edge:click', data)
        this.$data.clickNode = data
        this.$data.dialogVisible = true
      })
      this.lf.on('element:click', () => {
        this.hideAddPanel()
      })
      this.lf.on('edge:add', ({data}) => {
        console.log('edge:add', data)
      })
      this.lf.on('node:mousemove', ({data}) => {
        console.log('node:mousemove')
        this.moveData = data
      })
      this.lf.on('blank:click', () => {
        this.hideAddPanel()
      })
      this.lf.on('connection:not-allowed', (data) => {
        this.$message({
          type: 'error',
          message: data.msg
        })
      })
      this.lf.on('node:mousemove', () => {
        console.log('on mousemove')
      })
    },
    clickPlus(e, attributes) {
      e.stopPropagation()
      console.log('clickPlus', e, attributes)
      const {clientX, clientY} = e
      console.log(clientX, clientY)
      this.$data.addPanelStyle.top = (clientY - 40) + 'px'
      this.$data.addPanelStyle.left = clientX + 'px'
      this.$data.showAddPanel = true
      this.$data.addClickNode = attributes
    },
    mouseDownPlus(e, attributes) {
      e.stopPropagation()
      console.log('mouseDownPlus', e, attributes)
    },
    hideAddPanel() {
      this.$data.showAddPanel = false
      this.$data.addPanelStyle.top = 0
      this.$data.addPanelStyle.left = 0
      this.$data.addClickNode = null
    },
    closeDialog() {
      this.$data.dialogVisible = false
    },
    $_catData() {
      this.$data.graphData = this.$data.lf.getGraphData();
      this.$data.dataVisible = true;
    },
    $_catStateMachineData() {
      let graphData = this.$data.lf.getGraphData();
      let result = graphData.edges.map((edge) => {
        const sourceNode = graphData.nodes.find((node) => node.id === edge.sourceNodeId);
        const targetNode = graphData.nodes.find((node) => node.id === edge.targetNodeId);

        return {
          from: sourceNode.properties.code,
          to: targetNode.properties.code,
          event: edge.properties.code,
        };
      });
      console.log(result)

      this.$data.graphData = result;
      console.log(this.$data.graphData)

      this.$data.dataVisible = true;
    },
    $_catStateEventData() {
      let stateEvent = {
        state: [],
        event: [],
      }
      let graphData = this.$data.lf.getGraphData();
      stateEvent.state = graphData.nodes.reduce((uniqueNodes, node) => {
        const key = node.properties.text + node.properties.code;
        if (!uniqueNodes.some(n => n.text + n.code === key)) {
          uniqueNodes.push({
            text: node.properties.text,
            code: node.properties.code,
          });
        }
        return uniqueNodes;
      }, []);

      stateEvent.event = graphData.edges.reduce((uniqueEdges, edge) => {
        const key = edge.properties.text + edge.properties.code;
        if (!uniqueEdges.some(n => n.text + n.code === key)) {
          uniqueEdges.push({
            text: edge.properties.text,
            code: edge.properties.code,
          });
        }
        return uniqueEdges;
      }, []);
      this.$data.graphData = stateEvent;
      this.$data.dataVisible = true;
    }
  }
}
</script>
<style>
.logic-flow-view {
  height: 100vh;
  position: relative;
}

.demo-title {
  text-align: center;
  margin: 20px;
}

.demo-control {
  position: absolute;
  top: 50px;
  right: 50px;
  z-index: 2;
}

#LF-view {
  width: calc(100% - 100px);
  height: 80%;
  outline: none;
  margin-left: 50px;
}

.time-plus {
  cursor: pointer;
}

.add-panel {
  position: absolute;
  z-index: 11;
  background-color: white;
  padding: 10px 5px;
}

.el-drawer__body {
  height: 80%;
  overflow: auto;
  margin-top: -30px;
  z-index: 3;
}

@keyframes lf_animate_dash {
  to {
    stroke-dashoffset: 0;
  }
}
</style>

