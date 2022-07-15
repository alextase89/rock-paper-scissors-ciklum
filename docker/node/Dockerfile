FROM node:16.16.0-alpine
RUN mkdir /home/node/app && chown -R node:node /home/node/app
WORKDIR /home/node/app
COPY ./rock-paper-scissor-front/package.json ./
COPY ./rock-paper-scissor-front/yarn.lock ./
RUN yarn install
COPY --chown=node:node ./rock-paper-scissor-front .
ENV REACT_APP_API_PORT=${REACT_APP_API_PORT}
ENV REACT_APP_API_ENDPOINT=${REACT_APP_API_ENDPOINT}
USER node
CMD ["yarn", "start"]