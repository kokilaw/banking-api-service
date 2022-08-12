# Specification

* [API Specification](#api-specification)
* [Design Decisions](#design-decisions)

## API Specification

### User Resource

#### Create User

```http
  POST /v1/users
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `givenName`| Request Body | `string` | Given name of the user. |
| `familyName`| Request Body | `string` | Family name of the user. |
| `dateOfBirth`| Request Body | `string` | User's date of birth in `yyyy-MM-dd` format. |
| `nic`| Request Body | `string` | National identy card number of user which contains 10 characters. |
| `email`| Request Body | `string` | Email address of the user. |

#### Get User

```http
  GET /v1/users/{userId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Path | `integer` | Id of the user needs to be fetched. |

#### Update User

```http
  PUT /v1/users/{userId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Path | `integer` | Id of the user needs to be updated. |
| `givenName`| Request Body | `string` | Given name of the user. |
| `familyName`| Request Body | `string` | Family name of the user. |
| `dateOfBirth`| Request Body | `string` | User's date of birth in `yyyy-MM-dd` format. |
| `nic`| Request Body | `string` | National identity card number of user which contains 10 characters. |
| `email`| Request Body | `string` | Email address of the user. |

#### Delete User

```http
  DELETE /v1/users/{userId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Path and Request Body| `integer` | Id of the user needs to be deleted. |

### Account Resource

#### Create Account

```http
  POST /v1/accounts
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `userId`| Request Body | `integer` | User Id of the user which the account belongs to. |
| `currencyCode`| Request Body | `string` | Currency of the account in `ISO 4217` standard. |
| `balanceInCents`| Request Body | `integer` | Current balance of the account converted to cents. |

#### Get Account

```http
  POST /v1/accounts/{accountId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `accountId`| Path | `integer` | Id of the account needs to fetched. |

#### Update Account

```http
  PUT /v1/accounts/{accountId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `currencyCode`| Request Body | `string` | Currency of the account in `ISO 4217` standard. |
| `balanceInCents`| Request Body | `integer` | Current balance of the account converted to cents. |

#### Delete Account

```http
  DELETE /v1/accounts/{accountId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `accountId`| Path | `integer` | Id of the account. |

### Transaction Resource

#### Get all Transactions for an Account

```http
  GET /v1/accounts/{accountId}/transactions
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `accountId`| Path | `integer` | Id of the account needs to fetched. |

#### Create Transaction for an Account

```http
  POST /v1/accounts/{accountId}/transactions
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `accountId`| Path | `integer` | Id of the account needs to fetched. |
| `transactionType`| Request Body | `string` | Type of the transaction. Possible values are `CREDIT` or `DEBIT` |
| `amountInCents`| Request Body | `integer` | Transaction amount converted to cents. |

#### Get Transaction

```http
  GET /v1/accounts/{accountId}/transactions/{transactionId}
```

| Parameter | Param Type | Type     | Description                |
| :-------- | :-------- | :------- | :------------------------- |
| `accountId`| Path | `integer` | Id of the account transaction belongs to. |
| `transactionId`| Path | `integer` | Id of the transaction needs to fetched. |

## Design Decisions

### Storing monetary values in cents rather than decimal values

In order to avoid decimal precision related problems that may arise, system is accepting and storing the monetary values in cents.