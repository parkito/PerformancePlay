FROM postgres
ENV POSTGRES_USER user
ENV POSTGRES_PASSWORD passord
ENV POSTGRES_DB db
ADD init.sql /docker-entrypoint-initdb.d/
EXPOSE 5432