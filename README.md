# State Machine Practice

## Overwrite

在项目中经常会涉及到状态变更的问题，常见解决方案就是①分支法②查表法③状态机
如何工程逻辑复杂，将会面临，状态流转稀里糊涂，工程状态随意set。

抽象的看状态变更涉及如下内容

1. 状态: 当前状态、目标状态
2. 事件：用于出发当前状态到目标状态流转
3. 流转条件(Condition)
4. 流转动作(Action)

最终我们期望工程是怎么样的？

1. 产研沟通后输出流程图，即数据存储什么状态，在哪些动作出发状态A->B。
2. 流程图可以转化为java代码，枚举值，避免在工程中有魔法值。
3. 流程图的哪些状态可以变更，应该是动态的，基于配置中心或者配置文件，即创建状态机支持文件配置而非硬编码。
4. 期望状态机的变更也支持输出plantuml，方便大模型支持。
5. 期望状态机的流转是可以记录的，即在前端上可以快速展示出来。
6. 增强：期望状态机是支持以key维度的多版本控制的，当存在历史状态的状态机时，应该将历史流程走完。


## 当前状态

前端完成state+event前端拖拉拽，后端完成导入json进行单测部分，具体管理端部分感兴趣的可以贡献PR，MVP已完成

在线地址 [state-machine-template-factory](https://boommanpro.github.io/state-machine-template-factory/) 托管在githubPages
页面截图示例:

![img](https://raw.githubusercontent.com/BoomManPro/state-machine-template-factory/main/docs/front.png)

## UI

1. 期望有一个状态机生成器的增删改查系统，可以维护状态机的生成，方便长期管理。
2. 期望以状态机名称为key，支持状态机的version版本管理和版本差异判断。
3. 状态机的维护需要具备拖拉拽生成的能力。
4. 可以根据package-name等参数信息，生成模板java代码。

## Server

数据库

1. state_machine_template_instance
2. state_machine_template_instance_version
