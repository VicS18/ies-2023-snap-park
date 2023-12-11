# SnapPark

## Project Abstract
Snap Park is an application that aims to provide parking lot owners and users monitoring and managing utilities over them such as viewing current occupation. 

## Project team
- Team Manager : Tiago Pereira - 108546 
- Architect : Diogo Marto - 108298
- DevOps master : Vítor Santos - 107186
- Product Owner : Diogo Gaitas - 73259

## Container Setup Guide

### CockroachDB

Please use this tutorial as your main source of information. 
There are specific instructions that need some clarification, which will be provided here.

==FOLLOW THE SINGLE-NODE CONTAINER INSTRUCTIONS==

[CockroachDB Container Setup](https://www.cockroachlabs.com/docs/v23.1/start-a-local-cluster-in-docker-linux#step-2-start-the-cluster)

1. [Install the official Docker image](https://www.cockroachlabs.com/docs/v23.1/install-cockroachdb-linux#install-docker)

2. Create a bridge network
```bash
$ docker network create -d bridge roachnet
```

3. Create volume for persistency
```bash
$ docker volume create roach-single
```

4. Run the container

==NOTICE:== Environment variables WILL be sourced from a .env file in the future.

```bash
docker run -d   \
    --env COCKROACH_DATABASE="snap_park"   \
    --env COCKROACH_USER="root"   \
    --env COCKROACH_PASSWORD="snap_park" \            
    --name=roach-single   \
    --hostname=roach-single   \
    --net=roachnet   \
    -p 26257:26257   \
    -p 8080:8080   \
    -v "roach-single:/cockroach/cockroach-data" \
    cockroachdb/cockroach:v23.1.12 start-single-node \
    --http-addr=localhost:8080 \
    --insecure \
```

5. Get useful startup details from the container's logs
```bash
$ docker exec -it roach-single grep 'node starting' /cockroach/cockroach-data/logs/cockroach.log -A 11
```

6. Run interactive SQL command line on the container

Use the connection string (url) printed in the "sql:" parameter as seen in the output of the previous command.

```bash
$ docker exec -it roach-single ./cockroach sql --url="postgresql://root@roach-single:26257/defaultdb?sslcert=certs%2Fclient.root.crt&sslkey=certs%2Fclient.root.key&sslmode=verify-full&sslrootcert=certs%2Fca.crt"
```

When in doubt, revisit the linked tutorial. 

## Bookmarks

### Project Report (OneDrive)

https://uapt33090-my.sharepoint.com/:w:/g/personal/vitor_mtsantos_ua_pt/EVC90Zs7Yg9Hn26MMijkhGEBQuZp0UmTPTvXxaIweOdYlg?e=zxqqgN

### Development Repository
https://snappark.atlassian.net/jira/software/projects/SNAP/boards/1/backlog?epics=visible

### Project Backlog

https://github.com/users/VicS18/projects/1/views/1
