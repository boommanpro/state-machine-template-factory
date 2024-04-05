<template>
  <div class="container">
    <el-button class="copy-btn float-right" @click="copyContent">复制内容到剪切板</el-button>
    <vue-json-pretty class="json-container" :path="'res'" :data="graphData" :height="500"></vue-json-pretty>
  </div>
</template>
<script>
import VueJsonPretty from 'vue-json-pretty';
import 'vue-json-pretty/lib/styles.css';
import ClipboardJS from 'clipboard'

export default {
  props: {
    graphData: Object
  },
  components: {
    VueJsonPretty,
  },
  methods: {
    copyContent() {
      navigator.clipboard.writeText(JSON.stringify(this.graphData));
    }
  }
};
</script>
<style scoped>

.copy-btn.float-right {
  float: right;
}
.json-container{
  /* 设置一个固定的height或max-height，或者百分比高度，以限制内容区域的高度 */
  height: 60vh; /* 或者 height: 50vh; 或 max-height: 50vh; 等 */

  /* 当内容超出设定的高度时，显示垂直滚动条 */
  overflow-y: auto;
}
</style>
