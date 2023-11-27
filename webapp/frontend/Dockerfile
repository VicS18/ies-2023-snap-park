# Stage 1: Build SvelteKit application
FROM node:16 as build
WORKDIR /app
COPY ./snappark /app
RUN npm install
RUN npm run build

# Stage 2: Create a lightweight image with only the built assets
FROM node:14-alpine
WORKDIR /app
COPY --from=build /app /app
RUN npm install -g sirv

EXPOSE 5000
CMD ["sirv", "start", "--no-clear", "--port", "5000"]
