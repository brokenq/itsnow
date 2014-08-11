Itsnow平台模块测试开发指南
========================

1 单元测试
-------------

### 1.1 模型测试

这些测试用例针对 `dnt.itsnow.model` 包中模型对象，以`AccountTest`为例。
一般需要测试模型的以下三个方面:

* 模型完整性

测试该项内容之前，开发者需要设置好JSR-161的Validator（平台已经配置好所有相关依赖）

```
    static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    static Validator validator = factory.getValidator();
```
对于已经采用JSR声明约束的模型：

```
public abstract class Account extends Record {
    @NotBlank
    private String        sn;
    @NotBlank
    // 帐户名称
    @Size(min = 4, max = 50)
    private String        name;
    //...
}
```

典型的测试内容如下：

```
    @Test
    public void testEmptyAccountIsNotValid() throws Exception {
        account.setSn(null);
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertFalse(violations.isEmpty());
    }
    
    @Test
    public void testCorrectAccountIsValid() throws Exception {
        account.setSn("msu-001");
        account.setName("Test");
        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        Assert.assertTrue(violations == null || violations.isEmpty());
    }    
```

* JSON序列化

该类测试用例主要测试模型对象是否可以被系统配置的jackson完好的 toJson/fromJson，尤其当模型存在继承关系，引用关系时，典型的测试用例如下：

```
    @Test
    public void testJson() throws Exception {
        String json = JsonSupport.toJSONString(account);
        System.out.println(json);
        Account parsed = JsonSupport.parseJson(json, Account.class);
        Assert.assertTrue(parsed instanceof MsuAccount);
    }
```

* 业务方法

针对模型提供的业务方法进行测试，如：

```
    @Test
    public void testApply() throws Exception {
        Account another = new MsuAccount();
        another.setSn("msu-010");
        another.setName("another-msu");
        account.apply(another);
        Assert.assertEquals("msu-010", account.getSn());
        Assert.assertEquals("another-msu", account.getName());
    }
```


### 1.2 Repository测试

本类型测试用例主要测试数据库映射是否正确，Mybatis映射语句是否正确，为了实现该类型测试，以 `CommonAccountRepositoryTest` 为例

```
@ContextConfiguration(classes = CommonAccountRepositoryConfig.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonAccountRepositoryTest {
    @Autowired
    CommonAccountRepository repository;

    @Test
    public void testFindByName() throws Exception {
        Assert.notNull(repository.findByName("Itsnow Carrier"));
        Assert.isNull(repository.findByName("Not Exist"));
    }
    //...
}
```

需要注意：

* 需要为相应的测试类提供一个单独的Spring配置对象 `CommonAccountRepositoryConfig`
* 需要激活 test Profile，以便使用测试数据库(内存/H2)
* 需要用 `SpringJUnit4ClassRunner` 运行测试用例
* 被测试对象直接 `Autowired` 注入

其中，最关键的是 `CommonAccountRepositoryConfig` 对象，其代码如下：

```
@Configuration
@Import(DatabaseConfig.class)
public class CommonAccountRepositoryConfig extends RepositoryConfigWithH2 {

    protected String[] sqlScripts() {
        return new String[]{
                "classpath:META-INF/setup/prepare_schema.sql",
                "classpath:META-INF/migrate/20140722112724_create_accounts.sql@up",
                "classpath:META-INF/migrate/20140728142611_insert_accounts.sql@up"
        };
    }
}
```	

需要注意：

* 导入平台 DatabaseConfig
* 实现 sqlScripts方法，如果需要复用 migrate script，需要在脚本后面增加 @up, @down 后缀(代表只执行其中的up|down部分)

另外，测试者可以通过覆盖

```
    protected  String dbRepository() {
        return "dnt.itsnow.repository";
    }
```

方法制订额外的db仓库（支持以逗号，分号分割多个路径）

如果在特定仓库中，需要排除/限制仅扫描特定几个repository，则需要覆盖 `repositoryFilter` 方法，如下：

```
    public BeanFilter repositoryFilter(){      
        return new BeanFilter(){
		    public boolean accept(String beanName, BeanDefinition definition) {
        		return !beanName.equals("commonAccountRepository");
    		}
        };
    }
```

### 1.3 Service测试

一般而言，Service模块依赖Repository模块，为了进行Service测试，有两种方式：

* 方式1：对依赖的 repository 进行伪造(Mock)
* 方式2: 在测试环境中，将Repository模块构建出来(基于Memory DB)

#### 1.3.1 基于Mock的Service测试

* 需要为相应的测试类提供一个单独的Spring配置对象 `MutableAccountManagerConfig1`，在本对象中将mock对象，被测试对象等均构建好
* 被测试对象直接 `Autowired` 注入测试程序 `MutableAccountManagerTest1`
* 需要用 `SpringJUnit4ClassRunner` 运行测试用例

Config类

```
@Configuration
public class MutableAccountManagerConfig1 {

    @Bean
    public Mockery junit4Mockery(){
        return  new JUnit4Mockery();
    }
    // 被测试对象
    @Bean
    public MutableAccountService commonAccountService(){
        return new MutableAccountManager();
    }

	// mock对象
    @Bean
    public MutableAccountRepository mutableAccountRepository(){
        return junit4Mockery().mock(MutableAccountRepository.class);
    }
}
```

测试类（采用Jmock）:

```
@ContextConfiguration(classes = MutableAccountManagerConfig1.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableAccountManagerTest1 {
    // 被测试对象
    @Autowired
    MutableAccountService mutableAccountService;

    @Autowired
    MutableAccountRepository mutableAccountRepository;

    // Mock 相关
    @Autowired
    Mockery mockery;

    @Test
    public void testCreate() throws Exception {
        Expectations expectations = new Expectations(){{
            allowing(mutableAccountRepository).create(with(any(Account.class)));
            allowing(mutableAccountRepository).findBySn(with(any(String.class)));
            will(returnValue(account));
        }};
        mockery.checking(expectations);

        //验证会自动生成sn
        mutableAccountService.create(account);
        Assert.assertNotNull(account.getSn());
        Assert.assertEquals(AccountStatus.New, account.getStatus());

    }
}
```

#### 1.3.2 基于H2 Repository的Service测试

这种方式的测试，关键在于测试类的config是从RepositoryConfig继承，如下：

```
public class MutableAccountManagerConfig2 extends MutableAccountRepositoryConfig {
    @Bean
    public MutableAccountService commonAccountService(){
        return new MutableAccountManager();
    }

    @Bean
    public AutoNumberService autoNumberService(){
        return new AutoNumberInMemory();
    }
}
```

而后测试过程，可以直接基于内存数据库，不需要mock，这种测试用例特别适用于严重依赖底层Repository API的manager：

```
@ContextConfiguration(classes = MutableAccountManagerConfig2.class)
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class MutableAccountManagerTest2 {
    @Autowired
    MutableAccountService mutableAccountService;
    PageRequest pageRequest;
    Account account;

    @Before
    public void setUp() throws Exception {
        pageRequest = new PageRequest(0, 1);
        account = new MsuAccount();
        account.setName("Test Account");
        account.setStatus(AccountStatus.New);
    }

    @Test
    public void testFindAll() throws Exception {
        Page<Account> msus = mutableAccountService.findAll("msu", pageRequest);
        Assert.assertEquals(2, msus.getTotalElements());
        Assert.assertEquals(1, msus.getNumberOfElements());
    }
}
```

备注：

```
  究竟采用哪种测试方式，取决于建立测试环境的代价以及被测试程序的逻辑要求；一般而言，选择代价较小的测试方案；
  例如，如果service严重依赖底层repository的数十个api，mock时需要mock许多方法，此时，将repository层构建在内存数据库上就比较合算；
  又如，如果service仅仅是依赖了底层某几个api，而建立数据库表结构，以及数据相当麻烦，此时，采用mock技术就比较合算。
```

### 1.4 控制器测试

一般而言，控制器对service层有依赖，service对repository层又有依赖，所以，为控制器测试备齐所有底层环境比较困难，所以，控制器测试一般基于service的mock，其configration与上述服务层测试的方式1类似。

```
@Configuration
public class MutableAccountsControllerConfig extends ApplicationControllerConfig {

    // Mocked service beans

    @Bean
    public MutableAccountService mutableAccountService(){
        return EasyMock.createMock(MutableAccountService.class);
    }

    @Bean
    @Qualifier("plainUserService")
    public CommonUserService commonUserService(){
        return EasyMock.createMock(CommonUserService.class);
    }

    //这个也不用scanner，
    @Bean
    public MutableAccountsController mutableAccountsController(){
        return new MutableAccountsController();
    }
}
```

对应于平台提供的 `ApplicationController`, `SessionSupportedController`，控制器测试也提供了 `ApplicationControllerTest`（在spring mvc test框架中注入运行application controller所需要的interceptors）, `SessionSupportedControllerTest`(初始化user/account)， 根据被测试的控制器情况，使用者需要从合适的类继承，并直接`@Autowired`注入被mock的service以及被测试的类。

```
import static org.easymock.EasyMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ContextConfiguration(classes = MutableAccountsControllerConfig.class)
public class MutableAccountsControllerTest extends SessionSupportedControllerTest {

    @Autowired
    CommonUserService userService;
    @Autowired
    MutableAccountService accountService;

}
```

基于EasyMock的测试可以：

```
    @Test
    public void testIndex() throws Exception {
        // Service Mock 记录阶段
        expect(accountService.findAll(anyString(), isA(PageRequest.class)))
                .andReturn(new DefaultPage<Account>(accounts));

        // 准备 Mock Request
        MockHttpServletRequestBuilder request = get("/admin/api/accounts");
        decorate(request);

        // Mock 准备播放
        replay(accountService);

        // 执行
        ResultActions result = this.browser.perform(request);

        // 对业务结果的验证
        decorate(result).andExpect(status().isOk());
    }
    
    // 每次测试结束之后再验证
    @After
    public void tearDown() throws Exception {
        // 对Mock的Expectations进行验证
        verify(accountService);
    }
```

其中的 decorate(request|result) 均为相应父类提供的共享方法，用于设置当前用户/MIME Types等

2 集成测试
-------------

## 2.1 集成测试用例的组织

由于集成测试需要依赖被部署的系统，其测试环境比较难以搭建；按照Maven建议的最佳实践方式，将所有的集成测试用例组织到一个单独的模块 backend/integration-test模块，开发者需要激活 with-itsnow-it profile.

在 integration-test目录下，按照被部署的单元性质，分为：

* it-platform
* it-msc
* it-msu
* it-msp

四个独立的模块，分别对应于对不同的部署实体进行单独的集成测试(其中it-platform可以/需要运行于任意部署实体上)

开发者在编写集成测试用例时，需要根据实际API的提供者归属/部署的子系统，将集成测试用例放在相应的模块。

## 2.2 集成测试的编写

当前的集成测试用例，基于Spring Rest Template，采用 `dnt.itsnow.it.AbstractTest` 封装

支持用户对测试环境/用户进行配置：

```
public class GlobalTest extends AbstractTest{
    @Override
    protected void configure(Configuration configuration) {
        configuration
                .host("192.168.21.153")
                .port(8072)
                .username("jay.xiong")
                .password("secret");
    }
}
```

配置之后，对目标系统的REST API获取可以直接以URI方式（省去主机，端口）

```
    @Test
    public void testGetRoutes() throws Exception {
        String routes = getForObject("/routes", String.class);
        String[] routesArray = StringUtils.split(routes, "\r");
        Assert.assertTrue(routesArray.length > 3);
    }
```

如果被测试的REST接口，是POST类型接口，被Spring Security的CSRF保护，则提供了 `withCsrf` 方式：

```
    @Test
    public void testLoginWithCsrf() throws Exception {
        URI uri = super.withCsrf(new Callback<URI>() {
            public URI perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return postForLocation("/api/session?username={username}&password={password}", request, loginRequest);
            }
        });
        Assert.assertNotNull(uri);
    }
```

备注：

```
  withCsrf 函数也支持无返回值的Job回调方式
```

如果被测试的REST接口，需要有已经登录的用户身份，我们提供了 `withLoginUser`的方式：

```
    // 测试用户读取自身profile的url
    @Test
    public void testGetProfile() throws Exception {
        User user = withLoginUser(new Callback<User>() {
            @Override
            public User perform(HttpHeaders headers) {
                HttpEntity request = new HttpEntity(headers);
                return getForObject("/api/profile", User.class, request);
            }
        });
        Assert.assertNotNull(user);
        Assert.assertEquals(configuration.getUsername(), user.getUsername());
    }
```

其中的用户名/密码，在configure函数中设置，默认为 admin/secret

## 2.3 集成测试用例的执行

在持续集成系统(Teamcity)中，将会以脚本方式，先启动被测试系统，而后按照普通的maven测试；

在开发者环境，请开发者先自行启动相应的测试环境，而后通过ide或者maven米命令行启动相应的测试用例，其中，被测试主机/端口可以在不修改代码的情况下，通过设置系统 `it.host`, `it.port` 属性加以修改

3 接收测试
-------------

TBD

4 测试驱动开发
--------------

TBD