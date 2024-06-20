# Gajang API
진화 프로세스를 관찰하세요.


https://seelab.notion.site/c28fa1147317416097b82a13d48037c0?v=3e2d9c887b6a42b8b7f39718945bd39b&pvs=4

## Gajang API Docs
with Swagger:
https://gajang.shbox.kr/swagger-ui/index.html

with Notion: 
https://seelab.notion.site/Gajang-API-Document-db2e6852c2a84ccc9dbc0ff6ee77e12d?pvs=4

login, logout은 Spring security에 의해 관리되므로 Swagger에 보이지 않습니다. 해당 기능은 Notion을 참고해주세요.

# Setting
1. src/main/resources/config/database.properties 파일을 생성해주세요.
2. 아래와 같이 작성해주세요
```properties
db.url = db 주소
db.username = 데이터베이스 아이디
db.password = 데이터베이스 비밀번호
```
3. Database 폴더 내에 sql들을 등록해주세요.
# Docker-compose
최초 실행 시
``` shell
docker-compose up
```

특정 컨테이너만 빌드할 시
```shell
docker-compose up db // MySQL
또는
docker-compose up backend // API Server
```

특정 컨테이너를 아예 새롭게 빌드하는 경우 (소스코드 수정 후 war빌드 반영을 위함)
```shell
docker-compose up backend --build --force-recreate
```
