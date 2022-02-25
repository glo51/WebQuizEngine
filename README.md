# WebQuizEngine

It's an online restful application that allows to create and solve quizzes with multiple answers.
All the data (users, quizzes and completion history) is saved in H2 local database quizdb.mv.db.

User can make following api calls:
  - GET:
    - /api/quizzes/{id}       to get a quiz
    - /api/quizzes            to get all quizzes paging by 10, use request parameter "page" to get certain page
    - /api/quizzes/completed  to get all the quizzes that the current user succeed in, it's also pageable and receives "page" parameter
  - POST:
    - /api/quizzes              to post own quiz and become its author
    - /api/quizzes/{id}/solve   to solve a quiz
    - /api/register             to register to the database and Spring Security to be able to make other api calls
  - DELETE:
    - /api/quizzes/{id}   to delete a quiz that you own

Application requires valid input json and basic http authentication. The app has implemented Spring Security that requires to authenticate in every endpoint except from registering. Users' passwords are encrypted.
