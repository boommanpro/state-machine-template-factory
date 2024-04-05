<template>
  <div class="right-collapse-container" v-if="isExpanded">
    <!-- 折叠面板内容 -->
    <div ref="panel" :class="{ 'collapsed': !isExpanded }" :style="{ width: panelWidth + 'px' }">
      <h3>{{ title }}</h3>
      <el-icon
          class="close-icon"
          :size="25"
          @click="togglePanel"
      >
        <CloseBold/>
      </el-icon>
      <slot></slot>
    </div>
  </div>
  <el-icon v-if="!isExpanded" class="el-button-toggle-panel" @click="togglePanel">
    <DArrowLeft/>
  </el-icon>

</template>

<script>
import {CloseBold, DArrowLeft, Delete} from "@element-plus/icons-vue";

export default {
  name: 'RightCollapse',
  components: {DArrowLeft, CloseBold, Delete},
  props: {
    initialWidth: {
      type: Number,
      default: 300,
    },
    title: '',

  },
  emits: ['closePanel'],
  data() {
    return {
      panelWidth: this.initialWidth,
      isExpanded: true,
    };
  },

  methods: {
    togglePanel() {
      this.isExpanded = !this.isExpanded;
      if (!this.isExpanded) {
        this.$emit('closePanel')
      }
    },
    handleEscKeydown(event) {
      if (event.key === 'Escape') {
        this.togglePanel()
      }
    },
  },
  mounted() {
    document.body.style.overflowX = 'hidden'; // 防止页面水平滚动
    document.addEventListener('keydown', this.handleEscKeydown);
  },
  beforeDestroy() {
    document.body.style.overflowX = 'auto'; // 恢复页面水平滚动
  }
};
</script>

<style scoped>


.collapsed {
  transform: translateX(100%);
  transition: transform 0.3s ease-in-out;
}

.right-collapse-container {
  position: fixed;
  top: 0;
  right: 0;
  height: 100%;
  overflow-x: hidden;
}

/* 未折叠状态下的面板样式 */
:not(.collapsed) {
  transform: translateX(0);
  transition: transform 0.3s ease-in-out;
  background-color: white; /* 设置背景色为白色 */
  z-index: 2000; /* 设置一个较高的数值，确保该元素在同级定位元素之上 */
}

.right-collapse-container::before {
  content: ""; /* 必须有内容，哪怕为空字符串 */
  position: absolute;
  top: 0; /* 从顶部开始 */
  left: 0; /* 在左侧放置线条 */
  width: 1px; /* 线条宽度为1像素 */
  height: 100%; /* 高度为容器高度的100%，确保覆盖整个高度 */
  background-color: #eaeaea; /* 灰色线条 */
}

.el-button-toggle-panel {
  position: fixed; /* 使用固定定位 */
  top: calc(50% - 1.5em); /* 让按钮垂直居中，"1.5em"为大致的按钮高度的一半 */
  right: 16px; /* 距离右侧边缘的距离，根据需要调整 */
  writing-mode: vertical-lr; /* 竖向展示文字 */

  /* 可选：为适应竖排文字，调整按钮的宽高比 */
  width: 2em; /* 根据实际需求调整按钮宽度 */
  height: auto;
  min-height: 2em;

  /* 可选：防止文字溢出，保持单行显示 */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

/* 若需要兼容旧版浏览器，可以添加 vendor 前缀 */
.el-button-toggle-panel {
  -webkit-writing-mode: vertical-lr;
}

.close-icon {
  position: fixed; /* 使用绝对定位 */
  top: 16px; /* 调整到合适的位置 */
  right: 16px; /* 调整到合适的位置 */
}
</style>
