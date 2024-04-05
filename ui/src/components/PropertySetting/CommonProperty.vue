<template>
  <div>
    <el-form label-width="80px" :model="formData">
      <el-form-item label="名称">
        <el-input v-model="formData.text"></el-input>
      </el-form-item>
      <el-form-item label="code">
        <el-input v-model="formData.code"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
export default {
  name: '',
  props: {
    nodeData: Object,
    lf: Object || String,
  },
  mounted() {
    const {properties, text} = this.$props.nodeData
    if (properties) {
      this.$data.formData = Object.assign({}, this.$data.formData, properties)
    }
    if (text && text.value) {
      this.$data.formData.text = text.value
    }
    if (text && text.value) {
      this.$data.text = text.value
    }
  }, watch: {
    nodeData: {
      deep: true, // 如果 `nodeData` 内部对象属性也可能发生变化，启用深度观察
      handler(newNodeData) {
        const {properties, text} = newNodeData;

        if (properties) {
          this.formData = Object.assign({}, this.formData, properties);
        }

        if (text && text.value) {
          this.formData.text = text.value;
          this.text = text.value;
        }
      },
    },
  },
  data() {
    return {
      text: '',
      formData: {
        text: '',
        code: '',
      }
    }
  },
  methods: {
    onSubmit() {
      const {id} = this.$props.nodeData
      this.$props.lf.setProperties(id, {
        ...this.$data.formData
      });
      this.$props.lf.updateText(id, this.$data.formData.text);
      this.$emit('onClose')
    },
  }
}
</script>
<style scoped>
</style>
