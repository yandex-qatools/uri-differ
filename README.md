URI Differ
===========

[![release](http://github-release-version.herokuapp.com/github/yandex-qatools/uri-differ/release.png?style=flat)](https://github.com/yandex-qatools/uri-differ/releases/latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/ru.lanwen.diff/uri-differ-lib/badge.png?style=flat)](https://maven-badges.herokuapp.com/maven-central/ru.lanwen.diff/uri-differ-lib)


Simple lib to find diffs between URI and represent it in pretty way.

##Quick start: 
  
### Use maven:
```xml
<dependency>
    <groupId>ru.lanwen.diff</groupId>
    <artifactId>uri-differ-lib</artifactId>
    <version>1.1</version>
</dependency>
```  

### Add some code:  

Get changes:  
```java
UriDiff changes = UriDiffer.diff()
    .expected("http://ya.ru").actual("http://yandex.ru").changes();
```

Print them: 
```java
String report = changes.report();
```

Use any report generator with implementing `ReportView`:
```java
String report = changes.report(DefaultUrlDiffView.withDefaultView());
```

What it returns:  

| Expected  | Actual | Report |
| ------------------------ | -------------------- | ---------------------------- |
| `httpf://ya.ru`            | `httpfs://ya.ru`       | `httpf[+s]://ya.ru`            |
| `httpfd://ya.ru`           | `http://ya.ru`         | `http[-fd]://ya.ru`            |  
| `httpfd://ya.ru`           | `httpgt://ya.ru`       | `http[fd->gt]://ya.ru`         |  
| `httpf://ya.ru`            | `http://ya.ru`         | `http[-f]://ya.ru`             |  
| `httpf://ya.ru`            | `httpg://ya.ru`        | `http[f->g]://ya.ru`           |  
| `htspf://ya.ru`            | `httpg://ya.ru`        | `ht[s->t]p[f->g]://ya.ru`      |  
| `htspf://ya.ru`            | `httpg://ya.ru/h`      | `ht[s->t]p[f->g]://ya.ru[+/h]` |  
| `http://ya.ru`             | `http://yandex.ru`     | `http://[ya->yandex].ru`       |  
| `http://yandex.ru`         | `http://yandex.com.tr` | `http://yandex.[ru->com.tr]`   |  
| `http://yandex.com`        | `http://yandex.com.tr` | `http://yandex.com.[+tr]`      |  
| `http://yandex.com.tr`     | `http://yandex.com`    | `http://yandex.com.[-tr]`      |  
| `http://yandex.com.tr.fr`  | `http://yandex.com`    | `http://yandex.com.[-tr.fr]`   |  

##Use filters:

```java
UriDiffer.diff()
    .expected("http://ya.ru").actual("https://yandex.ru/?ncrnd=2342")
    .filter(scheme(), param("ncrnd")).changes();
```

### Scheme filter:

To ignore any scheme, add filter `SchemeFilter.scheme()`.   
You can also specify which schemes are allowed: `SchemeFilter.scheme("http", "https")`.

### Parameter filter:

To ignore parameter with some name, just add `AnyParamValueFilter.param("param_name")`.  
You can also specify which values only can be ignored: `AnyParamValueFilter.param("param_name").ignore("val1", "val2")`.

