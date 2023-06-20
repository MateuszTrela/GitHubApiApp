# Spring Boot GitHub API Application
This is a Spring Boot application that provides an API to retrieve a list of GitHub repositories for a given username.  
The API returns information about repositories that are not forks, including the repository name, owner login, branch names, and the last commit SHA for each branch.

## API Endpoints
### Retrieve repositories by username
``
Endpoint: GET /api/v1/user/{username}
``

This endpoint retrieves a list of GitHub repositories for the given username.

Request
- Path Parameters:
  - username: The GitHub username of the user whose repositories you want to retrieve.
- Request Headers:
  - Accept: Must be set to "application/json".

Response
- Status Code: 200 (OK)
- Response Body: JSON object containing the repository information.

### Example response body:

```yaml
{
  "repositories": [
    {
      "name": "repository1",
      "ownerLogin": "user1",
      "branches": [
        {
          "name": "master",
          "lastCommitSha": "1234567890abcdef"
        },
        {
          "name": "feature-branch",
          "lastCommitSha": "0987654321fedcba"
        }
      ]
    },
    {
      "name": "repository2",
      "ownerLogin": "user1",
      "branches": [
        {
          "name": "main",
          "lastCommitSha": "abcdef1234567890"
        }
      ]
    }
  ]
}
```

### Error Responses
If an error occurs, the response will be in the following format:
```yaml
{
  "status": ${responseCode},
  "message": ${whyHasItHappened}
}
```

- status: The HTTP response code indicating the type of error that occurred.
- message: A descriptive message explaining why the error has occurred.
