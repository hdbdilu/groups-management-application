FROM node:13.12.0-alpine AS build
WORKDIR /app
COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY . ./
# start app
EXPOSE 3000
CMD ["npm", "start"]