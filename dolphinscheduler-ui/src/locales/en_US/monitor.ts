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

export default {
  master: {
    cpu_usage: 'CPU Usage',
    memory_usage: 'Memory Usage',
    disk_available: 'Disk Available',
    load_average: 'Load Average',
    create_time: 'Create Time',
    last_heartbeat_time: 'Last Heartbeat Time',
    directory_detail: 'Directory Detail',
    host: 'Host',
    directory: 'Directory',
    master_no_data_result_title: 'No Master Nodes Exist',
    master_no_data_result_desc:
      'Currently, there are no master nodes exist, please create a master node and refresh this page'
  },
  worker: {
    cpu_usage: 'CPU Usage',
    memory_usage: 'Memory Usage',
    disk_available: 'Disk Available',
    load_average: 'Load Average',
    create_time: 'Create Time',
    last_heartbeat_time: 'Last Heartbeat Time',
    directory_detail: 'Directory Detail',
    host: 'Host',
    directory: 'Directory',
    worker_no_data_result_title: 'No Worker Nodes Exist',
    worker_no_data_result_desc:
      'Currently, there are no worker nodes exist, please create a worker node and refresh this page'
  },
  db: {
    health_state: 'Health State',
    max_connections: 'Max Connections',
    threads_connections: 'Threads Connections',
    threads_running_connections: 'Threads Running Connections',
    db_no_data_result_title: 'No DB Nodes Exist',
    db_no_data_result_desc:
      'Currently, there are no DB nodes exist, please create a DB node and refresh this page'
  },
  statistics: {
    command_number_of_waiting_for_running:
      'Command Number Of Waiting For Running',
    failure_command_number: 'Failure Command Number'
  },
  audit_log: {
    user_name: 'User Name',
    resource_type: 'Resource Type',
    resource_name: 'Resource Name',
    operation_type: 'Operation Type',
    create_time: 'Create Time',
    start_time: 'Start Time',
    end_time: 'End Time',
    tenant_audit: 'Tenant Audit',
    user_audit: 'User Audit',
    alert_group_audit: 'Alert Group Audit',
    alert_plugin_instance_audit: 'Alert Plugin Instance Audit',
    worker_group_audit: 'Worker Group Audit',
    yarn_queue_audit: 'Yarn Queue Audit',
    env_audit: 'Environment Audit',
    cluster_audit: 'Cluster Audit',
    k8s_audit: 'K8S Namespace Audit',
    token_audit: 'Token Audit',
    datasource_audit: 'Datasource Audit',
    resource_audit: 'Resource Audit',
    udf_audit: 'UDF Audit',
    task_group_audit: 'Task Group Audit',
    task_group_queue_audit: 'Task Group Queue Audit',
    project_audit: 'Project Audit',
    process_definition_audit: 'Process Definition Audit',
    process_instance_audit: 'Process Instance Audit',
    task_definition_audit: 'Task Definition Audit',
    task_instance_audit: 'Task Instance Audit',
    access_audit: 'Access Audit',
    audit_log_audit: 'Audit Log Audit',
    data_analysis_audit: 'Data Analysis Audit',
    data_quality_audit: 'Data Quality Audit',
    favourite_audit: 'Favourite Audit',
    logger_audit: 'Logger Audit',
    monitor_audit: 'Monitor Audit',
    schedule_audit: 'Schedule Audit',
    ui_plugin_audit: 'UI Plugin Audit',
    workflow_lineage_audit: 'Workflow Lineage Audit',
    other_audit: 'Other Audit',
    create: 'Create',
    update: 'Update',
    delete: 'Delete',
    read: 'Read',
    login: 'Login',
    sign_out: 'SignOut',
    connect: 'Connect',
    auth: 'Auth',
    un_auth: 'UnAuth',
    execute: 'Execute',
    copy: 'Copy',
    move: 'Move',
    switch: 'Switch',
    offline: 'Offline',
    close: 'Close',
    force_success: 'ForceSuccess',
    stop: 'Stop',
    release: 'Release',
    download: 'Download',
    upload: 'Upload',
    relation: 'Relation',
    un_relation: 'UnRelation',
    other: 'Other'
  }
}
