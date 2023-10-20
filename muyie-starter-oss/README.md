# muyie-starter-oss

文件上传是项目开发中必不可少的事，传统的项目将文件上传到本地文件系统，不能解决分布式问题。

本模块集成了阿里云对象存储服务（OSS），并提供了统一的文件上传接口，减轻项目开发中涉及文件上传的代码开发工作，开箱即用。

## 快速开始

1）在 Spring Boot 项目中添加依赖配置：

```xml

<dependency>
  <groupId>com.github.zhycn</groupId>
  <artifactId>muyie-starter-oss</artifactId>
</dependency>
```

2）添加属性配置参数（主要是获取阿里云API Key）：

```properties
## Aliyun OSS Configuration
muyie.oss.context-path=/api/upload
muyie.oss.enabled=true
muyie.oss.endpoint=
muyie.oss.accessKeyId=
muyie.oss.secretAccessKey=
muyie.oss.securityToken=
# Bucket Profile
muyie.oss.buckets.s1.bucket=
muyie.oss.buckets.s1.dns=
muyie.oss.buckets.s1.cdn=
muyie.oss.buckets.s1.folder=tmp
muyie.oss.buckets.s1.suffixSupports=bmp,jpg,jpeg,gif,png,svg,webp
muyie.oss.buckets.s1.maxFiles=10
# Spring Multipart Configuration
spring.servlet.multipart.maxFileSize=1M
spring.servlet.multipart.maxRequestSize=10M
```

3）配置完成后，就可以直接使用API接口。几个主要的API说明：

- OssService - 服务端简单文件上传接口
- OssUploadController - 客户端简单文件上传接口
- OssKeyGenerator - 对象名称生成器（ObjectKey）
- OssUploadCallback - 上传成功回调接口

## 上传接口说明

OssUploadController 提供了4个通用的对象上传接口：

- POST /api/upload/single/{key} - 单文件上传
- POST /api/upload/multipart/{key} - 多文件上传
- POST /api/upload/url/{key} - 上传网络流（主要用于网络图片上传）
- POST /api/upload/object/{key} - 上传字节流（Base64编码的字节对象）

注意：

1. 文件上传要添加请求头：content-type=multipart/form-data
2. 通过属性配置参数 `muyie.oss.context-path=/api/upload` 可以修改接口地址

### 1.单文件上传

请求参数说明：

```
- String key - 指定上传的BucketKey（必填）
- MultipartFile file - 指定上传的文件（必填）
```

上传结果示例：

```
{
  "code": "00000",
  "message": "成功",
  "data": [
    {
      "bucket": "wzdd-icons-test",
      "objectKey": "tmp/20230725/94a5d70997464f6cb43632445fc607bb.jpeg",
      "objectUrl": "https://wzdd-icons-test.oss-xxx.aliyuncs.com/tmp/20230725/94a5d70997464f6cb43632445fc607bb.jpeg",
      "etag": "3EFD02389038F7E5727DA29A651BA3DC"
    }
  ],
  "success": true
}
```

### 2.多文件上传

请求参数说明：

```
- String key - 指定上传的BucketKey（必填）
- MultipartFile[] files - 指定上传的文件列表（必填）
```

上传结果示例：

```
{
  "code": "00000",
  "message": "成功",
  "data": [
    {
      "bucket": "wzdd-icons-test",
      "objectKey": "tmp/20230725/2babfa486406459596bce9f8146171d9.jpeg",
      "objectUrl": "https://wzdd-icons-test.oss-xxx.aliyuncs.com/tmp/20230725/2babfa486406459596bce9f8146171d9.jpeg",
      "etag": "EEB8BE4B0393E3665CADD24CB3AFD78F"
    },
    {
      "bucket": "wzdd-icons-test",
      "objectKey": "tmp/20230725/6e5ef48b293846b6a921efbcbed4fbba.jpeg",
      "objectUrl": "https://wzdd-icons-test.oss-xxx.aliyuncs.com/tmp/20230725/6e5ef48b293846b6a921efbcbed4fbba.jpeg",
      "etag": "B146F14ED31B454E4061C184B2BCE1FE"
    }
  ],
  "success": true
}
```

### 3.上传网络流

请求参数说明：

```
- String key - 指定上传的BucketKey（必填）
- String url - 网络资源（主要是图片）
- String suffix - 指定上传的后缀名（可选，默认截取URL中的后缀名）
```

上传结果示例：

```
{
  "code": "00000",
  "message": "成功",
  "data": {
    "bucket": "wzdd-icons-test",
    "objectKey": "tmp/20230725/e0917a716e9d44d6becf723fb46b5aa3.png",
    "objectUrl": "https://wzdd-icons-test.oss-xxx.aliyuncs.com/tmp/20230725/e0917a716e9d44d6becf723fb46b5aa3.png",
    "etag": "E835568EEC449792AAA8C593A404A558"
  },
  "success": true
}
```

### 4.上传字节流

请求参数说明：

```
- String key - 指定上传的BucketKey（必填）
- String object - Base64编码的字节对象
- String suffix - 指定上传的后缀名（必填）
```

上传结果示例：

```
{
  "code": "00000",
  "message": "成功",
  "data": {
    "bucket": "wzdd-icons-test",
    "objectKey": "tmp/20230725/9f3bee09f0fa48b29d0d47be3597115f.png",
    "objectUrl": "https://wzdd-icons-test.oss-xxx.aliyuncs.com/tmp/20230725/9f3bee09f0fa48b29d0d47be3597115f.png",
    "etag": "25D55AD283AA400AF464C76D713C07AD"
  },
  "success": true
}
```
