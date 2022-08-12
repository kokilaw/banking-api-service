### User Resource

#### Create User

```http
  POST /v1/users
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `givenName`| Request Body | `string` | Given name of the user |
| `familyName`| Request Body | `string` | Family name of the user |
| `dateOfBirth`| Request Body | `string` | User's date of birth in `yyyy-MM-dd` formart |
| `nic`| Request Body | `string` | National identy card number of user which contains 10 characters |
| `email`| Request Body | `string` | Email address of the user |

#### Get User

```http
  GET /v1/users/{userId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Path and Request Body| `string` | Id of the user |
| `givenName`| Request Body | `string` | Given name of the user |
| `familyName`| Request Body | `string` | Family name of the user |
| `dateOfBirth`| Request Body | `string` | User's date of birth in `yyyy-MM-dd` formart |
| `nic`| Request Body | `string` | National identy card number of user which contains 10 characters |
| `email`| Request Body | `string` | Email address of the user |

#### Update User

```http
  PUT /v1/users/{userId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Path and Request Body| `string` | Id of the user |
| `givenName`| Request Body | `string` | Given name of the user |
| `familyName`| Request Body | `string` | Family name of the user |
| `dateOfBirth`| Request Body | `string` | User's date of birth in `yyyy-MM-dd` formart |
| `nic`| Request Body | `string` | National identy card number of user which contains 10 characters |
| `email`| Request Body | `string` | Email address of the user |

#### Delete User

```http
  DELETE /v1/users/{userId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Path and Request Body| `string` | Id of the user |
