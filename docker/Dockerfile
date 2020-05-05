FROM ubuntu

RUN apt-get update

RUN apt-get install git -y

ADD application.yml /family-money-bot/src/main/resources

ADD entrypoint.sh /bin/entrypoint.sh

RUN chmod +x /bin/entrypoint.sh

ENTRYPOINT /bin/entrypoint.sh

