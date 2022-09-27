/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import {
  defineComponent,
  getCurrentInstance,
  onMounted,
  toRefs,
  watch
} from 'vue'
import {
  NSpace,
  NInput,
  NSelect,
  NDatePicker,
  NButton,
  NIcon,
  NDataTable,
  NPagination
} from 'naive-ui'
import { SearchOutlined } from '@vicons/antd'
import { useTable } from './use-table'
import { useI18n } from 'vue-i18n'
import Card from '@/components/card'

const AuditLog = defineComponent({
  name: 'audit-log',
  setup() {
    const { t, variables, getTableData, createColumns } = useTable()

    const requestTableData = () => {
      getTableData({
        pageSize: variables.pageSize,
        pageNo: variables.page,
        resourceType: variables.resourceType,
        operationType: variables.operationType,
        userName: variables.userName,
        datePickerRange: variables.datePickerRange
      })
    }

    const onUpdatePageSize = () => {
      variables.page = 1
      requestTableData()
    }

    const onSearch = () => {
      variables.page = 1
      requestTableData()
    }

    const trim = getCurrentInstance()?.appContext.config.globalProperties.trim

    onMounted(() => {
      createColumns(variables)
      requestTableData()
    })

    watch(useI18n().locale, () => {
      createColumns(variables)
    })

    return {
      t,
      ...toRefs(variables),
      requestTableData,
      onUpdatePageSize,
      onSearch,
      trim
    }
  },
  render() {
    const { t, requestTableData, onUpdatePageSize, onSearch, loadingRef } = this

    return (
      <NSpace vertical>
        <Card>
          <NSpace justify='end'>
            <NInput
              allowInput={this.trim}
              v-model={[this.userName, 'value']}
              size='small'
              placeholder={t('monitor.audit_log.user_name')}
              clearable
            />
            <NSelect
              v-model={[this.operationType, 'value']}
              size='small'
              options={[
                { value: 'CREATE', label: t('monitor.audit_log.create') },
                { value: 'UPDATE', label: t('monitor.audit_log.update') },
                { value: 'DELETE', label: t('monitor.audit_log.delete') },
                { value: 'READ', label: t('monitor.audit_log.read') },
                { value: 'LOGIN', label: t('monitor.audit_log.login') },
                { value: 'SIGNOUT', label: t('monitor.audit_log.sign_out') },
                { value: 'CONNECT', label: t('monitor.audit_log.connect') },
                { value: 'AUTH', label: t('monitor.audit_log.auth') },
                { value: 'UNAUTH', label: t('monitor.audit_log.un_auth') },
                { value: 'EXECUTE', label: t('monitor.audit_log.execute') },
                { value: 'COPY', label: t('monitor.audit_log.copy') },
                { value: 'MOVE', label: t('monitor.audit_log.move') },
                { value: 'SWITCH', label: t('monitor.audit_log.switch') },
                { value: 'OFFLINE', label: t('monitor.audit_log.offline') },
                { value: 'CLOSE', label: t('monitor.audit_log.close') },
                { value: 'FORCE_SUCCESS', label: t('monitor.audit_log.force_success') },
                { value: 'STOP', label: t('monitor.audit_log.stop') },
                { value: 'RELEASE', label: t('monitor.audit_log.release') },
                { value: 'DOWNLOAD', label: t('monitor.audit_log.download') },
                { value: 'UPLOAD', label: t('monitor.audit_log.upload') },
                { value: 'RELATION', label: t('monitor.audit_log.relation') },
                { value: 'UN_RELATION', label: t('monitor.audit_log.un_relation') },
                { value: 'OTHER', label: t('monitor.audit_log.other') }
              ]}
              placeholder={t('monitor.audit_log.operation_type')}
              style={{ width: '180px' }}
              clearable
            />
            <NSelect
              v-model={[this.resourceType, 'value']}
              size='small'
              options={[
                {
                  value: 'TENANT',
                  label: t('monitor.audit_log.tenant_audit')
                },
                {
                  value: 'USER',
                  label: t('monitor.audit_log.user_audit')
                },
                {
                  value: 'ALERT_GROUP',
                  label: t('monitor.audit_log.alert_group_audit')
                },
                {
                  value: 'ALERT_PLUGIN_INSTANCE',
                  label: t('monitor.audit_log.alert_plugin_instance_audit')
                },
                {
                  value: 'WORKER_GROUP',
                  label: t('monitor.audit_log.worker_group_audit')
                },
                {
                  value: 'YARN_QUEUE',
                  label: t('monitor.audit_log.yarn_queue_audit')
                },
                {
                  value: 'ENVIRONMENT',
                  label: t('monitor.audit_log.env_audit')
                },
                {
                  value: 'CLUSTER',
                  label: t('monitor.audit_log.cluster_audit')
                },
                {
                  value: 'K8S_NAMESPACE',
                  label: t('monitor.audit_log.k8s_audit')
                },
                {
                  value: 'ACCESS_TOKEN',
                  label: t('monitor.audit_log.token_audit')
                },
                {
                  value: 'DATASOURCE',
                  label: t('monitor.audit_log.datasource_audit')
                },
                {
                  value: 'RESOURCE',
                  label: t('monitor.audit_log.resource_audit')
                },
                {
                  value: 'UDF_FUNC',
                  label: t('monitor.audit_log.udf_audit')
                },
                {
                  value: 'TASK_GROUP',
                  label: t('monitor.audit_log.task_group_audit')
                },
                {
                  value: 'TASK_GROUP_QUEUE',
                  label: t('monitor.audit_log.task_group_queue_audit')
                },
                {
                  value: 'PROJECT',
                  label: t('monitor.audit_log.project_audit')
                },
                {
                  value: 'PROCESS_DEFINITION',
                  label: t('monitor.audit_log.process_definition_audit')
                },
                  {
                      value: 'PROCESS_INSTANCE',
                      label: t('monitor.audit_log.process_instance_audit')
                  },
                  {
                      value: 'TASK_DEFINITION',
                      label: t('monitor.audit_log.task_definition_audit')
                  },
                  {
                      value: 'TASK_INSTANCE',
                      label: t('monitor.audit_log.task_instance_audit')
                  },
                  {
                      value: 'ACCESS',
                      label: t('monitor.audit_log.access_audit')
                  },
                  {
                      value: 'AUDIT_LOG',
                      label: t('monitor.audit_log.audit_log_audit')
                  },
                  {
                      value: 'DATA_ANALYSIS',
                      label: t('monitor.audit_log.data_analysis_audit')
                  },
                  {
                      value: 'DATA_QUALITY',
                      label: t('monitor.audit_log.data_quality_audit')
                  },
                  {
                      value: 'FAVOURITE',
                      label: t('monitor.audit_log.favourite_audit')
                  },
                  {
                      value: 'LOGGER',
                      label: t('monitor.audit_log.logger_audit')
                  },
                  {
                      value: 'MONITOR',
                      label: t('monitor.audit_log.monitor_audit')
                  },
                  {
                      value: 'SCHEDULE',
                      label: t('monitor.audit_log.schedule_audit')
                  },
                  {
                      value: 'UI_PLUGIN',
                      label: t('monitor.audit_log.ui_plugin_audit')
                  },
                  {
                      value: 'WORKFLOW_LINEAGE',
                      label: t('monitor.audit_log.workflow_lineage_audit')
                  },
                  {
                      value: 'OTHER',
                      label: t('monitor.audit_log.other_audit')
                  }
              ]}
              placeholder={t('monitor.audit_log.resource_type')}
              style={{ width: '180px' }}
              clearable
            />
            <NDatePicker
              v-model={[this.datePickerRange, 'value']}
              type='datetimerange'
              size='small'
              start-placeholder={t('monitor.audit_log.start_time')}
              end-placeholder={t('monitor.audit_log.end_time')}
              clearable
            />
            <NButton size='small' type='primary' onClick={onSearch}>
              <NIcon>
                <SearchOutlined />
              </NIcon>
            </NButton>
          </NSpace>
        </Card>
        <Card title={t('menu.audit_log')}>
          <NSpace vertical>
            <NDataTable
              loading={loadingRef}
              columns={this.columns}
              data={this.tableData}
            />
            <NSpace justify='center'>
              <NPagination
                v-model:page={this.page}
                v-model:page-size={this.pageSize}
                page-count={this.totalPage}
                show-size-picker
                page-sizes={[10, 30, 50]}
                show-quick-jumper
                onUpdatePage={requestTableData}
                onUpdatePageSize={onUpdatePageSize}
              />
            </NSpace>
          </NSpace>
        </Card>
      </NSpace>
    )
  }
})

export default AuditLog
