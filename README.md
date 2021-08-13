# 天气预报-安卓

实训成品，天气数据来自[心知天气](https://www.seniverse.com/)，主要使用 `Kotlin` 进行开发

## 功能

- 天气预报
- 空气预报
- 农历节气信息
- 城市尾号限行
- （无关）华为HMSCore人像抠图技术的应用

## 应用框架

| 名称                                                | 作用                    |
| --------------------------------------------------- | ----------------------- |
| Room                                                | 本地 Sqlite 数据库框架  |
| MMKV                                                | 高性能本地 XML 存储框架 |
| Retrofit                                            | 声明式HTTP请求框架      |
| Amap                                                | 高德定位框架            |
| [开源脚手架](https://gitee.com/xuqingquan/Scaffold) |                         |
| [开源工具类](https://gitee.com/xuqingquan/utils)    | 部分                    |

项目内应用`ViewBinding`和`DataBinding`实现数据视图的双向绑定。

视图层应用`viewpager2`、`flexbox`

