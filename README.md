## Android 动态提交按钮
主要用来解决消息发送、信息提交等需要网络请求的复杂交互，防止重复提交相同的数据。

主要功能：

1. 检测标题框内容是否为空，为空则不提交内容，并抖动编辑框，提示内容为空；
2. 按钮有三种状态，发送、发送中和已发送，需手动切换状态（不太智能）；
3. 能够及时反馈发送状态（也是手动）；

第一版有诸多缺陷，不建议在项目中使用。
已经有一个用在项目中的成熟版本，后续更新上来。

注：项目需要v7包的支持，因为用到v7包的编辑框，不需要可以自行去除。
