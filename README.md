URI Differ
===========

Simple lib to find diffs between URI and represent it in pretty way.

How it works: 

```
"httpf://ya.ru", "httpfs://ya.ru" -> "httpf[+s]://ya.ru"});
"httpfd://ya.ru", "http://ya.ru" -> "http[-fd]://ya.ru"});
"httpfd://ya.ru", "httpgt://ya.ru" -> "http[fd->gt]://ya.ru"});
"httpf://ya.ru", "http://ya.ru" -> "http[-f]://ya.ru"});
"httpf://ya.ru", "httpg://ya.ru" -> "http[f->g]://ya.ru"});
"htspf://ya.ru", "httpg://ya.ru" -> "ht[s->t]p[f->g]://ya.ru"});
"htspf://ya.ru", "httpg://ya.ru/h" -> "ht[s->t]p[f->g]://ya.ru[+/h]"});
"http://ya.ru", "http://yandex.ru" -> "http://[ya->yandex].ru"});
"http://yandex.ru", "http://yandex.com.tr" -> "http://yandex.[ru->com.tr]"});
"http://yandex.com", "http://yandex.com.tr" -> "http://yandex.com.[+tr]"});
"http://yandex.com.tr", "http://yandex.com" -> "http://yandex.com.[-tr]"});
"http://yandex.com.tr.fr", "http://yandex.com" -> "http://yandex.com.[-tr.fr]"});
```