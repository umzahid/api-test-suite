# 🔌 REST API Test Suite

A comprehensive REST API testing framework built with **RestAssured 5 + TestNG + Jackson**, targeting the [reqres.in](https://reqres.in) public API sandbox. Covers full CRUD operations, authentication flows, negative cases, and response-time assertions.

## ✨ Features

- ✅ Full **CRUD** coverage — GET, POST, PUT, PATCH, DELETE
- ✅ **Auth flow** tests — login, register, missing-field negative cases
- ✅ **Response time** performance assertions
- ✅ **POJO-based** request/response mapping via Jackson
- ✅ **RequestSpecBuilder** — shared base URI, headers, content-type
- ✅ Request & response logging to file for debugging
- ✅ **Tag-based** test grouping — `smoke`, `regression`, `negative`, `performance`
- ✅ Parallel execution via TestNG `thread-count`
- ✅ CI-ready — no browser, no GUI dependencies

## 🧱 Tech Stack

| Tool | Version | Purpose |
|------|---------|---------|
| Java | 11 | Language |
| RestAssured | 5.3.2 | HTTP client + fluent assertions |
| TestNG | 7.8.0 | Test runner |
| Jackson | 2.15.3 | JSON ↔ POJO mapping |
| ExtentReports | 5.1.1 | HTML test reports |
| Maven | 3.9+ | Build & dependency management |

## 🚀 Getting Started

```bash
# Clone
git clone https://github.com/umairzahid/api-test-suite.git
cd api-test-suite

# Run smoke tests
mvn clean test -Dgroups="smoke"

# Run full suite
mvn clean test

# Run negative tests only
mvn clean test -Dgroups="negative"
```

## 📁 Project Structure

```
api-test-suite/
├── src/test/java/com/umair/api/
│   ├── models/
│   │   └── User.java               # POJO for User resource
│   ├── utils/
│   │   └── ApiConfig.java          # RestAssured base config + specs
│   └── tests/
│       ├── BaseApiTest.java         # Suite setup, spec injection
│       ├── UserApiTest.java         # Full CRUD + perf tests
│       └── AuthApiTest.java         # Login + register tests
├── reports/                         # API request/response logs
├── testng.xml
├── pom.xml
└── README.md
```

## 🔖 Test Groups

| Group | Description |
|-------|-------------|
| `smoke` | Core happy-path tests |
| `regression` | Full coverage including edge cases |
| `negative` | Invalid input / error scenarios |
| `performance` | Response time assertions |
| `auth` | Authentication endpoint tests |
| `get` / `post` / `put` / `patch` / `delete` | HTTP method filtering |

## 📊 Sample Test Output

```
✅ GET  /users          → 200 OK  (data[0].email contains @)
✅ GET  /users/2        → 200 OK  (id=2, first_name present)
✅ GET  /users/9999     → 404 Not Found
✅ POST /users          → 201 Created (id returned)
✅ PUT  /users/2        → 200 OK  (updatedAt present)
✅ PATCH /users/2       → 200 OK  (job updated)
✅ DELETE /users/2      → 204 No Content
✅ POST /login          → 200 OK  (token returned)
✅ POST /register       → 400 Bad Request (missing password)
✅ Response time        → 243ms < 3000ms ✓
```

## 🔧 CI/CD (GitHub Actions)

```yaml
- name: Run API Tests
  run: mvn clean test -Dgroups="smoke"

- name: Upload Test Report
  uses: actions/upload-artifact@v3
  with:
    name: api-test-report
    path: target/surefire-reports/
```

## 👨‍💻 Author

**Umair Zahid** — SDET  
📧 aumair525@gmail.com | 🔗 [LinkedIn]([https://linkedin.com/in/umair-zahid](https://www.linkedin.com/in/umairzahid2000/))
