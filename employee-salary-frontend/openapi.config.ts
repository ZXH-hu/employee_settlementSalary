const { generateService } = require("@umijs/openapi");

generateService({
  requestLibPath: "import request from '@/request'",
  schemaPath: "http://60.204.185.112:8101/api/v2/api-docs",
  serversPath: "./src",
});
