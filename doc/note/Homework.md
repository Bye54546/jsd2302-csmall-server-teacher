# JSD2302-第四阶段课后作业

## 【第1次】2023-05-11

在`csmall-product`中，实现以下功能：

- 新增品牌
  - 请求参数包括：name / pinyin / logo / description / keywords / sort / enable
  - 参数规则：name不允许为null，pinyin不允许为null，sort不允许为null，sort必须0~99之间，enable必须0~1之间
  - 业务规则：name值必须唯一，在执行插入之前，将sales / product_count / comment_count / positive_comment_count设置为0，将gmt_create / gmt_modified设置为当前时间再执行插入
- 新增类别
  - 请求参数包括：name / parent_id / keywords / sort / icon / enable / is_display
  - 参数规则：name不允许为null，parent_id必须正数，sort不允许为null，sort必须0~99之间，enable必须0~1之间，is_display必须0~1之间
  - 业务规则：name值必须唯一，在执行插入之前，将gmt_create / gmt_modified设置为当前时间再执行插入

以上功能需完成Mapper、Service、Controller对应的代码，及Mapper、Service的测试，全部完成后，通过API文档进行调试。

作业提交截止时间：明天（05-12）早上09:00