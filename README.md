# cafe-backend

```
$ cd docker/local-dev
$ docker-compose up
```

```
$ sbt
[playframework-startup] $ run
```

## API

### cafes

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

### create user

```
$ http -v post localhost:9000/auth/create email="fuga@example.com" password="password" name="mi12cp"
POST /auth/create HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 71
Content-Type: application/json
Host: localhost:9000
User-Agent: HTTPie/1.0.0

{
    "email": "fuga@example.com",
    "name": "mi12cp",
    "password": "password"
}

HTTP/1.1 200 OK
Content-Length: 35
Content-Type: application/json
Date: Sun, 15 Sep 2019 04:26:59 GMT
Referrer-Policy: origin-when-cross-origin, strict-origin-when-cross-origin
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-Permitted-Cross-Domain-Policies: master-only
X-XSS-Protection: 1; mode=block

{
    "id": 4,
    "user": "mi12cp"
}
```

### login

```
$ http -v post localhost:9000/auth/login email="fuga@example.com" password="password"
POST /auth/login HTTP/1.1
Accept: application/json, */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 53
Content-Type: application/json
Host: localhost:9000
User-Agent: HTTPie/1.0.0

{
    "email": "fuga@example.com",
    "password": "password"
}

HTTP/1.1 200 OK
Content-Length: 89
Content-Type: application/json
Date: Sun, 15 Sep 2019 04:27:08 GMT
Referrer-Policy: origin-when-cross-origin, strict-origin-when-cross-origin
X-Content-Type-Options: nosniff
X-Frame-Options: DENY
X-Permitted-Cross-Domain-Policies: master-only
X-XSS-Protection: 1; mode=block

{
    "token": "Bearer 86m+CsMg5baGEc~YPsLw62_.m72SyKKlaiNKLXHTe~XZoBw2y8y/LZefPdBEX2EA"
}

```
