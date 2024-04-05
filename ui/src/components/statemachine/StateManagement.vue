<script>

export default {
  name: 'StateManagement',
  props: {
    stateList: Array,
  },
  data() {
    return {
      formData: {
        name: '',
        value: '',
      },
      addDialogVisible: false,
    }
  },
  mounted() {
  },
  methods: {
    resetForm() {
      this.$refs.addForm.resetFields();
    },
    submitForm() {
      this.stateList.push({...this.formData})
      this.resetForm();
      this.addDialogVisible = false;
    },
  }
}
</script>

<template>
  <el-button>快速导入</el-button>
  <el-button @click="addDialogVisible = true">新增</el-button>
  <el-table :data="stateList">
    <el-table-column property="name" label="名称" width="150"/>
    <el-table-column property="value" label="值" width="200"/>
    <el-table-column property="操作" label="Address">
      <template #default>
        <el-button link type="primary" size="small"
        >Detail
        </el-button
        >
        <el-button link type="primary" size="small">Edit</el-button>
      </template>
    </el-table-column>
  </el-table>

  <el-dialog :title="'新增状态'" v-model="addDialogVisible" @close="resetForm">
    <el-form ref="addForm" :model="formData" label-width="80px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="formData.name"></el-input>
      </el-form-item>
      <el-form-item label="值" prop="value">
        <el-input v-model="formData.value"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="addDialogVisible = false">取消</el-button>
      <el-button type="primary" @click="submitForm">保存</el-button>
    </div>
  </el-dialog>
</template>

<style scoped>

</style>
