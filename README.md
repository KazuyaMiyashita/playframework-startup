# cafe-backend

```
$ cd docker/local-dev
$ docker-compose up
```

```
$ sbt
[playframework-startup] $ run
```

```
$ http get localhost:9000/cafes
HTTP/1.1 200 OK
Content-Length: 297
Content-Type: application/json
Date: Sat, 14 Sep 2019 04:29:02 GMT
Referrer-Policy: origin-when-cross-origin, strict-origin-when-cross-origin
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-Permitted-Cross-Domain-Policies: master-only
X-XSS-Protection: 1; mode=block

[
    {
        "latitude": 35.7435032,
        "longitude": 139.8000034,
        "main_pic": null,
        "name": "BUoY Cafe",
        "star": 4.5
    },
    {
        "latitude": 35.7380439,
        "longitude": 139.7574194,
        "main_pic": null,
        "name": "コメダ珈琲店 田端駅前店",
        "star": 3.0
    }
]
```
