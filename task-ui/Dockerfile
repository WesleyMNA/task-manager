FROM node:lts-alpine as build-stage

WORKDIR /app

COPY ./ /app
RUN mkdir -p /dist/

RUN npm config get proxy
RUN npm config rm proxy
RUN npm config rm https-proxy

RUN rm -f package-lock.json

RUN npm install --force

ENV NODE_ENV=production
ENV VUE_APP_BASE_URL=/
ENV VUE_APP_URL_BACKEND=/api/

RUN npm run build
RUN mv /app/dist /dist

FROM nginx:1.25.3-alpine as production

ENV TZ=America/Sao_Paulo
ENV TIMEZONE=America/Sao_Paulo

RUN apk add bash
COPY --from=build-stage /dist/* /usr/share/nginx/html/front-end

CMD ["nginx", "-g", "daemon off;"]
