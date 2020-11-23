# stage 1

FROM node:alpine AS my-app-build
WORKDIR /app
COPY . .
RUN npm ci && npm run build:prod

EXPOSE 4200
ENTRYPOINT npm start

