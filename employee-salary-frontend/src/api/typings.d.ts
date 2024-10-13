declare namespace API {
  type BaseResponseBoolean_ = {
    code?: number;
    data?: boolean;
    message?: string;
  };

  type BaseResponseEmployeeTimeManageVO_ = {
    code?: number;
    data?: EmployeeTimeManageVO;
    message?: string;
  };

  type BaseResponseEmployeeVO_ = {
    code?: number;
    data?: EmployeeVO;
    message?: string;
  };

  type BaseResponseLoginUserVO_ = {
    code?: number;
    data?: LoginUserVO;
    message?: string;
  };

  type BaseResponseLong_ = {
    code?: number;
    data?: number;
    message?: string;
  };

  type BaseResponsePageEmployee_ = {
    code?: number;
    data?: PageEmployee_;
    message?: string;
  };

  type BaseResponsePageEmployeeTimeManage_ = {
    code?: number;
    data?: PageEmployeeTimeManage_;
    message?: string;
  };

  type BaseResponsePageEmployeeTimeManageVO_ = {
    code?: number;
    data?: PageEmployeeTimeManageVO_;
    message?: string;
  };

  type BaseResponsePageEmployeeVO_ = {
    code?: number;
    data?: PageEmployeeVO_;
    message?: string;
  };

  type BaseResponsePageSalaryTable_ = {
    code?: number;
    data?: PageSalaryTable_;
    message?: string;
  };

  type BaseResponsePageSalaryTableVO_ = {
    code?: number;
    data?: PageSalaryTableVO_;
    message?: string;
  };

  type BaseResponsePageTimeManage_ = {
    code?: number;
    data?: PageTimeManage_;
    message?: string;
  };

  type BaseResponsePageTimeManageVO_ = {
    code?: number;
    data?: PageTimeManageVO_;
    message?: string;
  };

  type BaseResponsePageUser_ = {
    code?: number;
    data?: PageUser_;
    message?: string;
  };

  type BaseResponsePageUserVO_ = {
    code?: number;
    data?: PageUserVO_;
    message?: string;
  };

  type BaseResponseSalaryTableVO_ = {
    code?: number;
    data?: SalaryTableVO;
    message?: string;
  };

  type BaseResponseTimeManageVO_ = {
    code?: number;
    data?: TimeManageVO;
    message?: string;
  };

  type BaseResponseUser_ = {
    code?: number;
    data?: User;
    message?: string;
  };

  type BaseResponseUserVO_ = {
    code?: number;
    data?: UserVO;
    message?: string;
  };

  type DeleteRequest = {
    id?: number;
  };

  type Employee = {
    createTime?: string;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    isDelete?: number;
    salaryMath?: number;
    timeType?: string;
    updateTime?: string;
  };

  type EmployeeAddRequest = {
    employeeName?: string;
    salaryMath?: number;
    timeType?: string;
  };

  type EmployeeQueryRequest = {
    current?: number;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    pageSize?: number;
    salaryMath?: number;
    sortField?: string;
    sortOrder?: string;
    timeType?: string;
  };

  type EmployeeTimeManage = {
    createTime?: string;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    isDelete?: number;
    salaryMath?: number;
    timeType?: string;
    updateTime?: string;
    workTime?: number;
  };

  type EmployeeTimeManageAddRequest = {
    employeeName?: string;
    employeeNo?: string;
    salaryMath?: number;
    timeType?: string;
    workTime?: number;
  };

  type EmployeeTimeManageQueryRequest = {
    current?: number;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    timeType?: string;
    workTime?: number;
  };

  type EmployeeTimeManageUpdateRequest = {
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    salaryMath?: number;
    timeType?: string;
    workTime?: number;
  };

  type EmployeeTimeManageVO = {
    createTime?: string;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    salaryMath?: number;
    timeType?: string;
    updateTime?: string;
    workTime?: number;
  };

  type EmployeeUpdateRequest = {
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    salaryMath?: number;
    timeType?: string;
  };

  type EmployeeVO = {
    createTime?: string;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    salaryMath?: number;
    timeType?: string;
    updateTime?: string;
  };

  type getCaptchaUsingGETParams = {
    /** userKey */
    userKey: string;
  };

  type getEmployeeTimeManageVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getEmployeeVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getMathCaptchaUsingGETParams = {
    /** userKey */
    userKey: string;
  };

  type getSalaryTableVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getTimeManageVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getUserByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type getUserVOByIdUsingGETParams = {
    /** id */
    id?: number;
  };

  type LoginUserVO = {
    createTime?: string;
    id?: number;
    updateTime?: string;
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type OrderItem = {
    asc?: boolean;
    column?: string;
  };

  type PageEmployee_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: Employee[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageEmployeeTimeManage_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: EmployeeTimeManage[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageEmployeeTimeManageVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: EmployeeTimeManageVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageEmployeeVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: EmployeeVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageSalaryTable_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: SalaryTable[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageSalaryTableVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: SalaryTableVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageTimeManage_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: TimeManage[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageTimeManageVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: TimeManageVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageUser_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: User[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type PageUserVO_ = {
    countId?: string;
    current?: number;
    maxLimit?: number;
    optimizeCountSql?: boolean;
    orders?: OrderItem[];
    pages?: number;
    records?: UserVO[];
    searchCount?: boolean;
    size?: number;
    total?: number;
  };

  type SalaryTable = {
    createTime?: string;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    isDelete?: number;
    salaryMath?: number;
    settlementMonth?: string;
    updateTime?: string;
    workMoney?: number;
    workTime?: number;
  };

  type SalaryTableAddRequest = {
    employeeName?: string;
    employeeNo?: string;
    salaryMath?: number;
    settlementMonth?: string;
  };

  type SalaryTableQueryRequest = {
    current?: number;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    pageSize?: number;
    salaryMath?: number;
    settlementMonth?: string;
    sortField?: string;
    sortOrder?: string;
    workTime?: number;
  };

  type SalaryTableUpdateRequest = {
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    salaryMath?: number;
    settlementMonth?: string;
    workMoney?: number;
    workTime?: number;
  };

  type SalaryTableVO = {
    createTime?: string;
    employeeName?: string;
    employeeNo?: string;
    id?: number;
    salaryMath?: number;
    settlementMonth?: string;
    updateTime?: string;
    workMoney?: number;
    workTime?: number;
  };

  type TimeManage = {
    createTime?: string;
    id?: number;
    isDelete?: number;
    runStatus?: number;
    salaryMath?: number;
    timeType?: string;
    updateTime?: string;
  };

  type TimeManageAddRequest = {
    runStatus?: number;
    salaryMath?: number;
    timeType?: string;
  };

  type TimeManageQueryRequest = {
    current?: number;
    id?: number;
    pageSize?: number;
    runStatus?: number;
    salaryMath?: number;
    sortField?: string;
    sortOrder?: string;
    timeType?: string;
  };

  type TimeManageUpdateRequest = {
    id?: number;
    runStatus?: number;
    salaryMath?: number;
    timeType?: string;
  };

  type TimeManageVO = {
    createTime?: string;
    id?: number;
    runStatus?: number;
    salaryMath?: number;
    timeType?: string;
    updateTime?: string;
  };

  type User = {
    createTime?: string;
    id?: number;
    isDelete?: number;
    updateTime?: string;
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userPassword?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserAddRequest = {
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userRole?: string;
  };

  type UserLoginRequest = {
    userAccount?: string;
    userPassword?: string;
  };

  type UserQueryRequest = {
    current?: number;
    id?: number;
    mpOpenId?: string;
    pageSize?: number;
    sortField?: string;
    sortOrder?: string;
    unionId?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserRegisterRequest = {
    checkPassword?: string;
    userAccount?: string;
    userNickName?: string;
    userPassword?: string;
  };

  type UserUpdateMyRequest = {
    captureCode?: string;
    checkPassword?: string;
    id?: number;
    userAccount?: string;
    userKey?: string;
    userPassword?: string;
  };

  type UserUpdateRequest = {
    id?: number;
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };

  type UserVO = {
    createTime?: string;
    id?: number;
    userAccount?: string;
    userAvatar?: string;
    userName?: string;
    userProfile?: string;
    userRole?: string;
  };
}
