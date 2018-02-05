## The Java example app

[![CircleCI](https://circleci.com/gh/contentful/the-example-app.java.svg?style=svg)](https://circleci.com/gh/contentful/the-example-app.java)

The Java example app teaches the very basics of how to work with Contentful:

- consume content from the Contentful Delivery and Preview APIs
- model content
- edit content through the Contentful web app

The app demonstrates how decoupling content from its presentation enables greater flexibility and facilitates shipping higher quality software more quickly.

![screenshot](https://images.contentful.com/88dyiqcr7go8/1ITbJQboPGmAOcEegiw20y/a9a045dff5be48bb6c09c375d33a2ed5/the-example-app-java.herokuapp.com.png)

You can see a hosted version of `The Java example app` on <a href="https://the-example-app-java.herokuapp.com/" target="_blank">Heroku</a>.

## What is Contentful?

[Contentful](https://www.contentful.com) provides a content infrastructure for digital teams to power content in websites, apps, and devices. Unlike a CMS, Contentful was built to integrate with the modern software stack. It offers a central hub for structured content, powerful management and delivery APIs, and a customizable web app that enable developers and content creators to ship digital products faster.

## Requirements

* Java (JDK 8+)
* Git
* Contentful CLI (only for write access)

Without any changes, this app is connected to a Contentful space with read-only access. To experience the full end-to-end Contentful experience, you need to connect the app to a Contentful space with read _and_ write access. This enables you to see how content editing in the Contentful web app works and how content changes propagate to this app.

## Common setup

Clone the repo and install the dependencies.

```bash
git clone https://github.com/contentful/the-example-app.java.git
```

## Steps for read-only access

To start the server, run the following

```bash
./gradlew run
```

Open [http://localhost:8080](http://localhost:8080) and take a look around.


## Steps for read and write access (recommended)

Step 1: Install the [Contentful CLI](https://www.npmjs.com/package/contentful-cli)

Step 2: Login to Contentful through the CLI. It will help you to create a [free account](https://www.contentful.com/sign-up/) if you don't have one already.
```
contentful login
```
Step 3: Create a new space
```
contentful space create --name 'My space for the example app'
```
Step 4: Seed the new space with the content model. Replace the `SPACE_ID` with the id returned from the create command executed in step 3
```
contentful space seed -s '<SPACE_ID>' -t the-example-app
```
Step 5: Head to the Contentful web app's API section and grab `SPACE_ID`, `DELIVERY_ACCESS_TOKEN`, `PREVIEW_ACCESS_TOKEN`.

Step 6: Open [application.properties](/src/main/resources/application.properties) and inject your credentials so it looks like this

```
spaceId=qz0n5cdakyl9
deliveryToken=580d5944194846b690dd89b630a1cb98a0eef6a19b860ef71efc37ee8076ddb8
previewToken=e8fc39d9661c7468d0285a7ff949f7a23539dd2e686fcb7bd84dc01b392d698b
version=1.0.0
application=The example Java app
```

Step 7: To start the server, run the following

```bash
./gradlew run
```

Final Step:

Open [http://localhost:8080?editorial_features=enabled](http://localhost:8080?editorial_features=enabled) and take a look around. This URL flag adds an _Edit_ button in the app on every editable piece of content which will take you back to Contentful web app where you can make changes. It also adds _Draft_ and _Pending Changes_ status indicators to all content if relevant.

## Deploy to Heroku
You can also deploy this app to Heroku:

[![Deploy](https://www.herokucdn.com/deploy/button.svg)](https://heroku.com/deploy)

