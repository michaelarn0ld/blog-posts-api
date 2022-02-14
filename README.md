# Blog Posts API

## Build from source
Execute the following from the root of the project.
```
mvn clean install
```
```
cd target
```
```
java -jar main.jar
```
The app will be available at `localhost:8080`

## Starting with the JAR
The simplest way to start this application is use the included `main.jar` file.
To do this, make sure you are in the root directory of the project and that the
`main.jar` file is visible. Verify you can see the app.jar by running the command:

```
ls | grep main.jar
```

If you see the `app.jar` in the output, then you are in the right spot. After this,
you must start the application by:

```
java -jar main.jar
```

Give it a moment; once it fully starts, it will be available at `localhost:8080`


## Starting with Docker
If you have docker on your machine, then you can also fire up the app in a container.
To get the image onto your machine, run the command:

```
docker pull michaelarn0ld/blog-api:1.0
```

Verify that you have the image on your machine by:

```
docker images
```

After you are sure you have the image, you may run a container for the app with
the following command:

```
docker run --rm -d -p 8080:8080 --name blog-post-app michaelarn0ld/blog-api:1.0
```

Check that the container is running (you should see its name) with:

```
docker ps
```

Awesome! Now the app is available at `localhost:8080`


## Endpoints
`localhost:8080` has no endpoints by itself; the first thing you should do to
make sure the app is running properly is to go to `localhost:8080/api/ping`. Visit
here in the browser and you should see the response:

```
{"success":true}
```

The other enpoint in the app is at `localhost:8080/api/post`. Visit here in the 
browser and you will receive the following:

```
{"errors":["Tags parameter is required"]}
```

The reason you are seeing this error is because you must have the `tag` query
parameter. This parameter allows you to fetch blog posts that have certain tags.
You may include several tags in your query, seperated by commas.

Single Tag:
```
localhost:8080/api/post?tag=tech
```

Multiple Tags:
```
localhost:8080/api/post?tag=tech,health
```

In addition to the `tag` query parameter, there are two other optional query
parameters: `sortBy` and `direction`. The `sortBy` parameter allows you to sort
results by a specific field of the post. The `direction` parameter allows you
to specify the direction of sort. Refer to the table below for usage:

* `sortBy`
    * id - sorts posts by id (default)
    * reads - sorts posts by how many times they have been read
    * likes - sorts posts by likes
    * popularity - sorts posts by popularity
```
localhost:8080/api/post?tag=tech&sortBy=reads
```

* `direction`
    * asc - sort ascending order (default)
    * desc - sort descending order
```
localhost:8080/api/post?tag=tech&sortBy=popularity&direction=desc
```

## Caveats
If you started with docker make sure to exit the container when you are done!

```
docker stop blog-post-app
```
