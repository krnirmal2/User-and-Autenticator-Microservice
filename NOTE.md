1. create 
          

          1. create models , dtsos , controllers, services
           https://github.com/jwtk/jjwt?tab=readme-ov-file#features

2. class creating JWT token for authenticating from server
   for muliple time login by user using token 
![img_2.png](img_2.png)
          

3. CLASS : DAY 205, 22 DEC
   Backend Projects: Implementing Search - Paging, Sorting, Elastic Search
           
            CONNECT TWO MICROSERVICES productServices and userService and done lot of  modification 
            there two connect them 
            JWTObject {
         +  // NOTE 24 UP:
           +  // this will return in the DTO
             +  // After validation of the validate method it should return
             +  // some kind of message or that validate successfully
          okenValidator {
            +  // NOTE 22 UP: this are the models which are common for both
              +  // microservices ProductService and UserService
              +  // this are kept in some common place to access by all
              +  // and this are actually service
              + NOTE 21 UP: add String authToken and refactor each place

       / if we need to extract the key value pair that is inside the token payload then we can
           // parse the token and find the correponding value like email, roles, date time etc
                  

             // NOTE 18:
+        // what are token validation need to validate for a particular thing to access
      +    // that should be present here