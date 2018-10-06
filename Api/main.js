var express = require('express');
var bodyParser = require('body-parser');
var app = express();
const {request} = require('graphql-request');
const api = 'https://kjsce-test.herokuapp.com/v1alpha1/graphql';

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.post('/', function (req, res) {
    console.log(req.body);
    let query = `{
        profiles {
            id
            name
        }
      }`;

    request(api, query).then(data => {
        res.json(data);
    });
});

// create user API
app.post('/create', function (req, res) {
    var name = req.body.name;
    var contact = req.body.contact;
    var ward = req.body.ward;
    var locality = req.body.locality;
    var email = req.body.email;

    console.log(`adding a new user with contact: ${contact}`);

    const insert = `mutation {
        insert_user (
          objects: [{
            name : "${name}",
            contact : "${contact}",
            ward : "${ward}",
            locality : "${locality}",
            email : "${email}",
          }]
        ) {
          affected_rows
        }
    }`
      
    request(api, insert).then(data => {
        res.json(data);
    });
});


// new post API
app.post('/newPost', function (req, res) {
    var title = req.body.title;
    var content = req.body.content;
    var content_type = req.body.content_type;
    var ward = req.body.ward;
    var category = req.body.category;
    var location_lat = req.body.location_lat;
    var location_long = req.body.location_long;
    var user_name = req.body.user_name;
    var user_id = req.body.user_id;

    location_lat = parseFloat(location_lat);
    location_long = parseFloat(location_long);

    console.log(`adding new post by ${user_id}`);

    const insert = `mutation {
        insert_post (
          objects: [{
            title : "${title}",
            content : "${content}",
            content_type : "${content_type}",
            ward : "${ward}",
            category : "${category}",
            location_lat : ${location_lat},
            location_long : ${location_long},
            user_name : "${user_name}",
            user_id : ${user_id}
          }]
        ) {
          affected_rows
        }
    }`
      
    request(api, insert).then(data => {
        res.json(data);
    });
});

// new comment API
app.post('/newComment', function (req, res) {
    var comment = req.body.comment;
    var post_id = req.body.post_id;
    var user_id = req.body.user_id;
    var user_name = req.body.user_name;
    console.log(`adding new comment by ${user_id} on ${post_id}`);

    const insert = `mutation {
        insert_comment (
          objects: [{
            comment : "${comment}",
            post_id : ${post_id},
            user_id : ${user_id},
            user_name : "${user_name}"
            }]
        ) {
          affected_rows
        }
    }`
      
    request(api, insert).then(data => {
        res.json(data);
    });
});

// upote API
app.post('/upvote', function (req, res) {
    var post_id = req.body.post_id;
    var user_name = req.body.user_name;
    var user_id = req.body.user_id;
    console.log(`adding new upvote by ${user_id} on ${post_id}`);

    const insert = `mutation {
        insert_upvotes (
          objects: [{
            post_id : ${post_id},
            user_name : "${user_name}",
            user_id : ${user_id}
          }]
        ) {
          affected_rows
        }
    }`
      
    request(api, insert).then(data => {
        res.json(data);
    });
});

// report API
app.post('/report', function (req, res) {
    var post_id = req.body.post_id;
    var user_name = req.body.user_name;
    var user_id = req.body.user_id;
    var reason = req.body.reason;
    console.log(`adding new report by ${user_id} on ${post_id} for reason ${reason}`);

    const insert = `mutation {
        insert_report (
          objects: [{
            post_id : ${post_id},
            reason : "${reason}",
            user_name : "${user_name}",
            user_id : ${user_id}
          }]
        ) {
          affected_rows
        }
    }`
      
    request(api, insert).then(data => {
        res.json(data);
    });
});


// fetch nearby
app.post('/nearby', function (req, res) {
    var lat_min = req.body.lat_min;
    var lat_max = req.body.lat_max;
    var long_min = req.body.long_min;
    var long_max = req.body.long_max;

    const query = `query {
        post (
          where: {
            _and: [
              {location_lat: {_gt: ${lat_min}}},
              {location_lat: {_lt: ${lat_max}}},
              {location_long: {_gt: ${long_min}}},
              {location_long: {_lt: ${long_max}}}
            ]
          },
          order_by: time_desc
        ) {
          id
          title
          content
          content_type
          category
          ward
          location_lat
          location_long
          status
          status_note
          user_id
          user_name
          time
          score
          type
          }
      }`
      
    request(api, query).then(data => {
        res.json(data);
    });
});


// fetch trending
app.post('/trending', function (req, res) {
    const query = `query {
        post (
          limit: 20,
          where: {score: {_gte: 20}},
          order_by: score_desc
        ) {
          id
          title
          content
          content_type
          category
          ward
          location_lat
          location_long
          status
          status_note
          user_id
          user_name
          time
          score
          type
          }
      }`
      
    request(api, query).then(data => {
        res.json(data);
    });
});


// fetch official
app.post('/official', function (req, res) {
    const query = `query {
        post (
          where: {type: {_eq: "official"}},
          order_by: time_desc
        ) {
          id
          title
          content
          content_type
          category
          ward
          location_lat
          location_long
          status
          status_note
          user_id
          user_name
          time
          }
      }`
      
    request(api, query).then(data => {
        res.json(data);
    });
});


// fetch comments
app.post('/getComments', function (req, res) {
    var post_id = req.body.post_id;
    console.log(post_id);
    post_id = parseInt(post_id);
    console.log(post_id);

    const query = `query {
        comment (
          where: {post_id: {_eq: ${post_id}}},
          order_by: time_desc
        ) {
          comment
          user_name
          time
          }
      }`

    request(api, query).then(data => {
        res.json(data);
    });
});


var server = app.listen(process.env.PORT || 80, function () {
  var host = server.address().address
  var port = server.address().port
  console.log("Example app listening at http://%s:%s", host, port)
})