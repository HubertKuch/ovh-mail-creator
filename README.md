## CLI for emails automation on OVH

### Requirements

Java 17 or higher

### Build

Linux/MacOS

```bash
git clone https://github.com/HubertKuch/ovh-mail-creator.git
cd ovh-mail-creator
./mvnw clean && ./mvnw install
```

Windows

```bash
git clone https://github.com/HubertKuch/ovh-mail-creator.git
cd ovh-mail-creator
.\mvnw.cmd clean && .\mvnw.cmd install
```

### Configuration

```yaml
ovh:
  app-secret: <>
  app-key: <>
  consumer-key: <>
  endpoint: <>
```

### Available commands

<details>
    <summary>create-email</summary>
Create X emails on specify domain

### Options

*`--count` How many emails create <br>
*`--domain` OVH domain <br>
*`--password` Password for emails <br>
*`--base` Base email name for example `test` and everyone mail will be created with
`test_{random ten letters}` <br>
`--size` Size in bytes <br>
`--description` Description
</details>

<details>
    <summary>get-emails</summary>
Get all emails

### Options

*`--domain` OVH domain <br>
</details>

<details>
    <summary>delete-emails</summary>
Delete all emails from domain

### Options

*`--domain` OVH domain <br>
</details>

### Generate json sample

```json
[
  {
    "id": 24142,
    "name": "test",
    "email": "test@test.com",
    "domain": "test.com",
    "password": "test"
  }
]
```
