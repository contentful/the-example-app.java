A simple Contentful setup consists of a client application reading content, such as this example app, and another application that is writing content, such as the Contentful web app. The client application is reading content by connecting to the Content Delivery API, and the Contentful Web app is writing content by connecting to the Content Mangement API:

![minimal contentful setup](//images.contentful.com/ft4tkuv7nwl0/3z7ErmBLIccwQkQkuEY0w4/bd438f4b8c540f56fcc76d75c688baf1/minimal-setup.svg)

The *Contentful web app* is a single page application that Contentful provides to help with the most common tasks when managing content:

- Provide an interface for editors to write content.
- Provide an interface for developes to configure a space, model data, define roles and permissions, and set up webhook notifications.

As mentioned, the Contentful web app is a client that uses the Content Management API. Therefore, you could replicate the functionality that the Contentful web app provides by making an API call. This is a powerful aspect of an API-first design because it helps you to connect Contentful to third-party systems.

