FROM node:18-alpine3.17 as build

RUN apk update && apk upgrade && adduser -D svelteuser
USER svelteuser

WORKDIR /app

COPY --chown=svelteuser:svelteuser . /app

RUN npm install && npm run build


FROM node:18-alpine3.17
RUN apk update && apk upgrade && apk add dumb-init && adduser -D svelteuser
USER svelteuser

WORKDIR /app

COPY --chown=svelteuser:svelteuser --from=build /app/build /app/package.json ./

EXPOSE 5000

ENV HOST=0.0.0.0 PORT=5000 NODE_ENV=production

CMD ["dumb-init","node","index.js"]