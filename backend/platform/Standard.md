Itsnow平台模块开发指南
===============

1 前言(Preface)
-------------

## 1.1 一般命名规范

|   场景               |   命名规范        |  典型样例  |
|---------------------|------------------|-----------|
| 单词规范             | 专有名词之间以下划线分割，word之间用破折号分割,如果不可以用破折号，则用下划线连词| Service Catalog -> service-catalog -> service_catalog |
| 数据库schema名称      | itsnow_$identify | itsnow_msc, itsnow_msu_001 |
| 数据库table名称       | 小写，复数，underscore | users, service_catalogs |
| 数据库列名称          | 小写，单数, under score| id, created_at |
| 数据库主键            | id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT | 就是左边样子 |
| 数据库外键            | 对方表名_id INT(10) UNSIGNED | account_id INT(10) UNSIGNED|
| 类注释               | 应以h1标记类主要说明 | &lt;h1&gt;类功能说明&lt;/h1&gt; |
| 方法注释             | 应以h2标记方法主要功能 | &lt;h2&gt;方法说明&lt;/h2&gt; |
| package名称          | 小写，underscore    | 严禁大写字符，破折号等字符等出现在package中 |
| 类名称               | Camel Case       | ManagedService, ServiceCatalog|
| 模型类名称            | 数据库对应表的Camel Case| contract_details -> ContractDetail |
| 模型类成员变量        | 数据库对应列的Camel Case(小写开头)| created_at -> createdAt |
| DAO类名称            | $Model + Repository | UserRepository |
| 服务接口名称          | $Model + Service    | UserService |
| 服务接口异常          | $Model + Exception  | UserException |
| 服务实现类            | $Model + Manager    | UserManager  |
| 控制器类名称          | $Models + "Controller"  | 如果控制器需要为当前用户管理该模型多个实例，应该用复数，如 `UsersController`， 否则用单数，如: `SessionController` |
| 控制器查询列表         | GET /api/ + $models | GET /api/users |
| 控制器查看对象         | GET /api/ + $models + /$identify | GET /api/users/jason |
| 控制器创建对象         | POST /api/$models  | POST /api/users |
| 控制器更新对象         | PUT /api/$models + /$identify | PUT /api/users/jason |
| 控制器删除对象         | DELETE /api/$models + /$identify  | DELETE /api/users/jason|
| Commom 模块控制器类    | Common + $Models + "Controller" | CommonUsersController |
| Common 模块服务接口    | Common + $Model + "Service" | CommonUserService |
| Common 模块服务实现    | Common + $Model + "Manager" | CommonUserManager |
| Common 模块DAO        | Common + $Model + "Repository" | CommonUserRepository |
| General 模块控制器类   | General + $Models + "Controller" | GeneralUsersController |
| General 模块服务接口   | General + $Model + "Service" | GeneralUserService |
| General 模块服务实现   | General + $Model + "Manager" | GeneralUserManager |
| General 模块DAO       | General + $Model + "Repository" | GeneralUserRepository |
| MSC 模块控制器类       | Mutable + $Models + "Controller" | MutableUsersController |
| MSC 模块服务接口       | Mutable + $Model + "Service" | MutableUserService |
| MSC 模块服务实现       | Mutable + $Model + "Manager" | MutableUserManager |
| MSC 模块DAO           | Mutable + $Model + "Repository" | MutableUserRepository |

2 标准模块构成
-------------

Itsnow的项目，在平台之外，包括如下大的模块：

| 模块               | 说明          |   建议     |
| ------------------|--------------|------------| 
| common-services   | 提供任意部署单元都会用到的通用服务 |关于 itsnow_msc的直接读服务, 关于每个部署单元的主schema均有的模型的管理服务（如 groups, acls, 工作流） |
| general-services  | 提供 msu,msp 两类部署单元公用的一般服务 | 关于 msu, msp的主schema中均有的模型的管理服务；指向msc的远程服务 |
| msc-services      | msc部署单元才用到的服务 | 面向 itsnow_msc 的管理服务，并以SPI的形式暴露给 general, msc, msp services 使用 |
| msu-services      | msu部署单元才用到的服务 | msu 独有的业务模型的管理服务 |
| msp-services      | msp部署单元才用到的服务 | msp 独有的业务模型的管理服务 |
| demo-services     | 平台开发时的演示模块    | 系统运行时不需要这些服务 |

每个大的模块下，可以按照业务领域，分为更细的子模块，如 msc-services 被分为了

* mutable-account-service
* mutable-contract-service
* mutable-service_catalog-service
* mutable-sla-service
* mutable-user-service

等5个子模块, 每个子模块，可以根据其被其他模块使用情况，以两种方式组织：

## 2.1 接口实现分离模式

这是一种比较复杂的模块组织方式，适用于以下场景：

  1. 本模块的服务api/model/exception会被其他部署单元中的类直接访问
  2. 本模块的服务实现并不能/也不该在其他部署单元中部署

假设 msc的mutable-account-service以RMI的方式直接提供AccountService服务，msu, msp作为RMI客户端直接远程调用该服务。
此时，mutable-account-service就需要以这种复杂方式组织，具体来讲：

该模块需要被分解为 mutable-account-api, mutable-account-app两个maven子模块；

其中mutable-account-api必须以api结尾（否则会被平台认为是一个业务服务而予以加载）；它应该被所有使用它，实现它的部署单元中的模块所依赖；
而mutable-account-app仅需要部署在msc实体中。

具体例子，可以参考 demo-services中的user-service模块

由于当前itsnow系统没有在部署单元之间直接使用RMI接口（而是REST接口），而通用模型(itsnow_msc.* slave schema，一般性table)又在每个部署单元中都可以访问，所以原则上，没有模块需要采用这个复杂模式部署。

## 2.2 接口实现混合模式

这是一种比较简单的模块组织方式，特定服务模块仅为一个maven module；适用于以下两种场景

  1. 本模块的服务是以SPI方式提供，与其他模块不存在直接的接口/模型/异常依赖(如msc的mutable模块)
  2. 本模块的服务虽然以API方式提供，但与其他使用的模块一定会部署在同一个部署单元中(如common模块)

如: 
  msc的mutable-user-service中的MutableUserService，它被其他模块以SPI通过REST接口访问
  common-user-service 中的 UserService，它会被部署在所有的部署单元，所以，没有必要将其api与实现分离。

## 2.3 模块开发

具体模块开发，请参考 [平台说明](http://git.happyonroad.net/insight/itsnow/blob/Dev-0.1.3/backend/platform/README.md)

3 数据访问层
-------------

### 3.1 业务模型

典型样例：

```java
package dnt.itsnow.model;

import dnt.itsnow.platform.model.Record;
import org.hibernate.validator.constraints.NotBlank;
import java.util.List;

/**
 * <h1>MSU和MSP之间关于服务的合同契约</h1>
 */
public class Contract extends Record {
    // 合同编号
    @NotBlank
    private String sn;
    //合同甲方，服务采购方
    @NotBlank
    private Long msuAccountId;
    private MsuAccount msuAccount;
    //合同乙方，服务供应方
    @NotBlank
    private Long mspAccountId;
    private MspAccount mspAccount;
    // MSU 是否批准
    private ContractStatus msuStatus;
    // MSP 是否批准
    private ContractStatus mspStatus;

    //合同明细
    private List<ContractDetail> details;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public ContractStatus getMsuStatus() {
        return msuStatus;
    }

    public void setMsuStatus(ContractStatus msuStatus) {
        this.msuStatus = msuStatus;
    }

    public ContractStatus getMspStatus() {
        return mspStatus;
    }

    public void setMspStatus(ContractStatus mspStatus) {
        this.mspStatus = mspStatus;
    }

    public Long getMsuAccountId() {
        return msuAccountId;
    }

    public void setMsuAccountId(Long msuAccountId) {
        this.msuAccountId = msuAccountId;
    }

    public Long getMspAccountId() {
        return mspAccountId;
    }

    public void setMspAccountId(Long mspAccountId) {
        this.mspAccountId = mspAccountId;
    }

    public MsuAccount getMsuAccount() {
        return msuAccount;
    }

    public void setMsuAccount(MsuAccount msuAccount) {
        this.msuAccount = msuAccount;
    }

    public MspAccount getMspAccount() {
        return mspAccount;
    }

    public void setMspAccount(MspAccount mspAccount) {
        this.mspAccount = mspAccount;
    }

    public List<ContractDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ContractDetail> details) {
        this.details = details;
    }

    public ContractDetail getDetail(Long id) {
        if(details == null ) return null;
        for (ContractDetail detail : details) {
            if(detail.getId().equals(id)) return detail;
        }
        return null;
    }

    public boolean isApprovedByMsu() {
        return msuStatus.isApproved();
    }

    public boolean isApprovedByMsp(){
        return mspStatus.isApproved();
    }
}
```

  1. 原则上业务模型均放置于 `dnt.itsnow.model` 包中
  2. 主业务模型类一般继承于 `dnt.itsnow.platform.model.Record` (其数据表不是中间表)
  3. 模型类，成员变量命名参考前言约束
  4. 模型成员上可以基于Java Validation API进行校验标记
  5. 应该为模型成员变量增加getter/setter
  6. 应该为模型类预留空构造函数
  7. 关联对象应该以成员方式读写
  8. 关联对象的外键也应该成员化
  9. 如果该业务模型对应的数据表被多类模块(common, general, msc, msu, msp)查询/管理，仅应该建立一个模型，并将该模型的放在common或general模块中。

--


### 3.2 数据库Schema

1、 创建数据库表

典型样例：

```SQL
CREATE TABLE contract_details (
  id          INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
  contract_id INT(10) UNSIGNED NOT NULL,
  title       VARCHAR(255)     NOT NULL,
  brief       VARCHAR(255),
  description TEXT(16),
  icon        VARCHAR(100),
  created_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at  TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (contract_id)    REFERENCES contracts(id)
);
```   
 Check List：
 
    A. 列对齐，便于执行时在控制台查看
    B. 主键(id)，外键(xxx_id) 的类型均为 INT(10) UNSIGNED
    C. MySQL关键词，类型等均用大写
    D. 自身表名，列名均小写，underscore方式，参考前言规范
    E. 一般业务模型(实体，非关联表)，均应该增加 id(主键), created_at(记录创建时间), updated_at(记录更新时间)等缺省字段

2、 初始化数据

典型样例：

```SQL
SET @msc_id = (SELECT id from accounts where sn = 'msc');
SET @msu_001_id = (SELECT id from accounts where sn = 'msu_001');
SET @msp_001_id = (SELECT id from accounts where sn = 'msp_001');
SET @msp_002_id = (SELECT id from accounts where sn = 'msp_002');

INSERT INTO contracts(msu_account_id, msp_account_id, sn) VALUES
(@msu_001_id, @msp_002_id, 'SWG-201403080003'),
(@msu_001_id, @msp_001_id, 'SWG-201407010010'),
(@msu_001_id, @msp_001_id, 'SWG-201408050002');
```
Check List:
  
    A. 如果对其他表数据有依赖，一般不能依赖于自动创建的id，而是应该先根据特定唯一性条件选择出来
    B. 插入的列名称在INSERT第一行显性指明，不能依赖隐性的列顺序, VALUES也在第一行
    C. 多行数据，每行一条记录，并对齐列。

备注：

    如果该数据表被多类模块(common, general, msc, msu, msp)查询/管理，
    应将migrate脚本放在能够对其进行读写管理的模块中，如user的migrate脚本仅被放在msc/mutable-user-service模块

### 3.3 数据映射层
  
命名约束：

  1. 一般命名为 $Model + Repository
  2. 包名一般为 `dnt.itsnow.repository`
  3. 典型的数据访问方法为：统计(count)/查找单个(find)/查找多个(findAll)/增加(create)/更新(update)/保存(save)/删除(delete)
  4. 前缀与模型同名的Repository，不需要再额外增加模型名称，如 `UserRepository.findUser` 应该直接定义为 `UserRepository.find`
  5. 如果以上方法有根据条件，则应该加上 By + $field，如 `countByMsuAccountId(long)`, `findAllByName(String)`
  6. 如果多个条件，则应该为 By + $fieldA + And $fieldB，如 `countByNameAndAge(String,int)`
  7. 无论是Annotation还是XML中的SQL，均应该遵循SQL语句规范，数据库关键词/类型名均大写。

Mapping约定：

  1. 简单的映射语句，不需要Dynamic SQL能力的，直接以Annotation写在接口上
  2. 每个参数均应该用 `@Param` 声明名称，在mapping SQL中不应该以默认的 `param1`, `param2`引用
  3. 每个`@Param`声明的参数名称应该与参数变量名称一致（遵循java参数变量命名规范）
  4. 关联查询时，关联对象一般只需要传入id/外键，不需要将整个对象传入，如 `countByMsuAccountId(Long)` 而不是 `countByMsuAccount(Account)`

典型样例：

```java
package dnt.itsnow.repository;

import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Pageable;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <h1>合同仓库服务，包括对合同明细的查询能力</h1>
 */
public interface ContractRepository {
    @Select("SELECT count(0) FROM contracts WHERE msu_account_id = #{msuId}")
    long countByMsuAccountId(@Param("msuId") long msuId);


    @Select("SELECT count(0) FROM contracts WHERE msp_account_id = #{msuId}")
    long countByMspAccountId(@Param("mspId") long mspId);

    //不需要加载details，但需要分页

    List<Contract> findAllByMsuAccountId(@Param("msuId") long msuId, @Param("pageable")Pageable pageable);

    List<Contract> findAllByMspAccountId(@Param("mspId") long mspId, @Param("pageable")Pageable pageable);

    // 需要加载details
    Contract findBySn(String sn);
}
```

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="dnt.itsnow.repository.CommonContractRepository">
    <select id="findAllByMsuAccountId" resultType="Contract">
        SELECT * FROM itsnow_msc.contracts
        WHERE (msu_account_id = #{msuId})
        <if test="pageable.sort != null">
            ORDER BY #{pageable.sort}
        </if>
        <trim prefix="LIMIT" prefixOverrides=",">
            <if test="pageable.offset > 0">#{pageable.offset}</if>
            <if test="pageable.pageSize > 0">, #{pageable.pageSize}</if>
        </trim>
    </select>
    <select id="findAllByMspAccountId" resultType="Contract">
        SELECT * FROM itsnow_msc.contracts
        WHERE (msp_account_id = #{mspId})
        <if test="pageable.sort != null">
            ORDER BY #{pageable.sort}
        </if>
        <trim prefix="LIMIT" prefixOverrides=",">
            <if test="pageable.offset > 0">#{pageable.offset}</if>
            <if test="pageable.pageSize > 0">, #{pageable.pageSize}</if>
        </trim>
    </select>

    <select id="findBySn" resultMap="contractWithDetails">
      SELECT c.*,
          d.id          AS detail_id,
          d.contract_id AS detail_contract_id,
          d.title       AS detail_title,
          d.brief       AS detail_brief,
          d.description AS detail_description,
          d.icon        AS detail_icon,
          d.created_at  AS detail_created_at,
          d.updated_at  AS detail_updated_at
      FROM contracts c
      LEFT OUTER JOIN contract_details d ON c.id = d.contract_id
      WHERE c.sn = #{sn}
    </select>

    <resultMap id="contractWithDetails" type="Contract" autoMapping="true">
        <id property="id" column="id"/>
        <collection property="details"
                    ofType="ContractDetail"
                    columnPrefix="detail_"
                    autoMapping="true"/>
    </resultMap>

</mapper>
```

4 业务层
----------

### 4.1 业务接口

1. 业务接口一般命名为 $Model + Service，如 `ContractService`
2. 业务接口一般存放于 `dnt.itsnow.service`中
3. 典型的接口方法为: `find`, `findAll`, `findPage`, `search`, `create`, `update`, `destroy`, `save`等
4. 与Repository的命名规则一样，如果Service的前缀与模型名称一致，则在以上方法后面不需要加上模型名称；
5. 业务方法上如果要抛出异常，则应该抛出业务异常 `ServiceException`或其子类
6. 业务异常可以不定义，如果需要定义，则一般被命名为 $Model + Exception，如 `ContractException`，存放于`dnt.itsnow.exception`包中，并继承 `dnt.itsnow.platform.exception.ServiceException`
7. 为若干个紧密相关的业务模型仅需要定义一个以主模型为中心的业务服务，如，存在 `Contract`, `ContractDetail`两个密切相关的业务模型，但只需要定义一个 `ContractService`，不需要再额外定义一个 `ContractDetailService`
8. 在以上例子中，如果存在对ContractDetail的操作，应该增加 `Detail`后缀，如: `updateDetail(ContractDetail detail)` (可以理解为：Contract一词被ContractService中的Contract抵消)
9. 如果存在分页请求，服务接口上应该返回 Page<$Model>，方法命名为 `findAll` 或者 `findPage`，最后一个参数为`Pageable`对象
10. create/update类型的接口，应该输入修改的对象，返回创建/更新后的对象
11. 接口设计时，应该根据使用场景考虑好 1+N 问题，如 查询/操作 `Contract`时是否包括其`ContractDetail`，这需要根据该业务模块的特点设计，但在接口注释上必须说明

示例:

```java
package dnt.itsnow.service;

import dnt.itsnow.model.Account;
import dnt.itsnow.model.Contract;
import dnt.itsnow.platform.service.Page;
import dnt.itsnow.platform.service.Pageable;
import dnt.itsnow.platform.service.ServiceException;

/**
 *  <h1>合同服务</h1>
 */
public interface ContractService {
    /**
     * 找到特定账户下的所有合同
     *
     * @param account  企业/提供商的账户
     * @param pageable 分页请求
     * @return 合同分页数据，不包括ContractDetail
     */
    Page<Contract> findAllByAccount(Account account, Pageable pageable) throws ServiceException;

    /**
     * 找到特定账户下的特定合同(sn)，根据指定参数决定是否加载合同明细
     *
     * @param account     企业/提供商的账户
     * @param sn          合同编号
     * @param withDetails 是否加载合同明细
     * @return 合同对象, 根据withDetail决定是否加载合同明细
     */
    Contract findByAccountAndSn(Account account, String sn, boolean withDetails) throws ServiceException;
    
    ContractDetail updateDetail(ContractDetail updating);
}
```


### 4.2 业务实现

1. 业务实现类一般命名为 $Model + "Manager"，并存放于 `dnt.itsnow.support`目录
2. 业务实现类一般都用 `@org.springframework.stereotype.Service` 标记，并被自动扫描
3. 业务实现类一般从 `dnt.spring` 下的`Bean`, `EventSupportBean`, `TranslateSupportBean`, `ApplicationSupportBean` 等类继承，并实现相应业务接口 $Model + “Service”
4. 业务实现类采用`@Autowired`对相应Repository的进行引用，如果两者模型一致，Repository取名为成员变量`repository`
5. 如果同一个业务模型被按照common/general/mutable分为了多个业务实现，则 general 应该继承 common, mutable也继承common
6. mutable manager里面对相应的repository的命名改为 `mutableRepository`
7. find/findAll等无损操作应该有DEBUG级别日志输出
8. create/update等有损操作应该有INFO级别日志
9. destroy等破坏性操作应该有WARN级别日志

示例：

```java
@Service
public class CommonAccountManager extends Bean implements CommonAccountService {

    @Autowired
    AccountRepository repository;

    @Override
    public Account findByName(@Param("name") String name) {
        logger.debug("Finding account by name {}", name);
        return repository.findByName(name);
    }

    @Override
    public Account findBySn(String sn) {
        logger.debug("Finding account by sn {}", sn);
        return repository.findBySn(sn);
    }

    @Override
    public Account findById(Long id) {
        logger.debug("Finding account by id {}", id);
        return repository.findById(id);
    }
}
```

```java
/**
 *  <h1> The mutable account manager</h1>
 *
 *  可修改的账户管理服务实现
 */
@Service
public class MutableAccountManager extends CommonAccountManager implements MutableAccountService {
    @Autowired
    MutableAccountRepository mutableRepository;
    
    @Override
    public Account create(Account account) {
        logger.info("Creating {}", account);
        account.setStatus(AccountStatus.New);
        long id = mutableRepository.create(account);
        Account created = mutableRepository.findById(id);
        logger.info("Created  {}", created);
        return created;
    }

    @Override
    public Account update(Account account) throws AccountException {
        logger.info("Updating {}", account);
        Account updating = mutableRepository.findBySn(account.getSn());
        if( updating.isExpired() )
            throw new AccountException("Can't update expired account: " + updating.getSn());
        updating.apply(account);
        mutableRepository.update(updating);
        logger.info("Updated  {}", updating);
        return updating;
    }

    @Override
    public void destroy(Account deleting) throws AccountException {
        logger.warn("Deleting account {}", deleting);
        if( deleting.isValid() )
            throw new AccountException("Can't delete valid account: " + deleting);
        mutableRepository.deleteBySn(deleting.getSn());
        logger.warn("Deleted  account {}", deleting);
    }
}
```

5 Web 控制器层
-------------

## 5.1 一般规范：

1. 控制器类一般命名为 $Model + "s" + "Controller"，并存放于 `dnt.itsnow.web.controller`目录
2. 控制器类一般都用 `@org.springframework.web.bind.annotation.RestController` 标记，并被自动扫描
3. 控制器一般应该被`@RequestMapping("/api/$models")`
4. 控制器类采用`@Autowired`对相应Servce的进行引用；
5. 控制器类一般从 继承`dnt.itsnow.platform.web.controller.ApplicationController`
6. 如果控制器类需要用到当前登录用户信息或者说只有登录用户才可以访问，则应该从 `dnt.itsnow.platform.web.controller.SessionSupportController`继承，这些控制器方法可以访问父类提供的成员变量 `currentUser`, `mainAccount` 获取到当前用户以及用户的主账户信息。
7. 控制器类的注释一般为如下格式（包括控制器说明，所有的对外SPI说明）

```java
/**
 * <h1>账户服务的控制器</h1>
 * <pre>
 * <b>HTTP     URI                            方法       含义  </b>
 * # GET      /admin/api/accounts            index     列出所有相关账户，支持过滤，分页，排序等
 * # GET      /admin/api/accounts/{sn}       show      列出特定的账户
 * # POST     /admin/api/accounts            create    创建账户，账户信息通过HTTP BODY提交
 * # PUT      /admin/api/accounts/{sn}       update    修改账户，账户信息通过HTTP BODY提交
 * # DELETE   /admin/api/accounts/{sn}       destroy   删除账户
 *
*/
```
**注意对齐！！**

8。 控制器方法的注释一般为如下格式（包括方法说明，访问方式，参数，返回值等）

```java
    /**
     * <h2>查看所有账户</h2>
     * <p/>
     * GET /admin/api/accounts?type={msc|msu|msp|*}&page={int}&size={int}
     *
     * @param type 账户类型，取值可以为 msc, msu, msp
     * @return 账户列表
     */
```


## 5.2 标准方法开发规范：

### 5.2.1 获取资源集合

URL入口为： GET /api/$models
   
应该被映射为 `public List<$Model> index()` 方法，并支持从request parameters中获取分页条件 `page`, `size`, `sort`
   
分页结果信息应该通过http response header输出。
   
如果有其他的查询需求，可以在index方法的参数中增加`@PathVariable`, `@RequestParam`

由于基类`ApplicationController`通过`@BeforeFilter`实现了为index方法解析分页信息：
   
```java
    // 通过 Before Filter 自动创建的page request对象
    protected PageRequest pageRequest;
    protected Page<T> indexPage;

    /**
     * <h2>初始化默认的分页请求</h2>
     */
    @BeforeFilter(method = RequestMethod.GET, value = "index")
    public void initDefaultPageRequest( @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                        @RequestParam(required = false, value = "size", defaultValue = "40") int size,
                                        @RequestParam(required = false, value = "sort", defaultValue = "") String sort){
        Sort theSort = parseSort(sort);
        pageRequest = new PageRequest(page, size, theSort);
    }
```
   
通过`@AfterFilter`实现了将当前获得的页面信息输出到http response头中
   
```java
    @AfterFilter(method =  RequestMethod.GET, value = "index")
    public void renderPageToHeader(HttpServletResponse response){
        if( indexPage == null ) return;
        response.setHeader(Page.TOTAL, String.valueOf(indexPage.getTotalElements()));
        response.setHeader(Page.PAGES, String.valueOf(indexPage.getTotalPages()));
        response.setHeader(Page.NUMBER, String.valueOf(indexPage.getNumber()));
        response.setHeader(Page.REAL, String.valueOf(indexPage.getNumberOfElements()));
        response.setHeader(Page.SORT, String.valueOf(indexPage.getSort()));
    }
```

所以典型的 index 可以实现为:
   
```java
public class AccountsController extends SessionSupportController<Account> {
    @Autowired
    AccountService accountService;
    /**
     * <h2>查看所有账户</h2>
     * <p/>
     * GET /admin/api/accounts?type={msc|msu|msp|*}&page={int}&size={int}
     *
     * @param type 账户类型，取值可以为 msc, msu, msp
     * @return 账户列表
     */
    @RequestMapping
    public List<Account> index( @RequestParam(value = "type", required = false) String type ) {
        logger.debug("Listing accounts");
        indexPage = accountService.findAll(type, pageRequest);
        logger.debug("Found   accounts {}", indexPage.getTotalElements());
        return indexPage.getContent();
    }
}
```

  请注意，以上获取的page必须赋值给成员变量 **indexPage**，这样才能保证`@AfterFilter`正确工作，方法直接返回的List&lt;Account&gt; 由`@RestController`的作用标记为`@ResponseBody`，并由Spring MVC自动找到jackson coverter转换为application/json MIME类型的JSON内容给客户端。
  
备注：

  以上正好也演示了 `@BeforeFilter`, `@AfterFilter` 这两个 **interceptor** 的使用，这两个annotation所标记的方法（如`initDefaultPageRequest`）必须是控制器的public成员方法，与一般的控制器方法一样，支持采用Spring MVC的标准Annotation(`@PathVariable`, `@RequestParam`,`@RequestBody`,`@RequestPart`,`@RequestHeader`,`@CookieValue`)等进行参数植入 
  
  `@BeforeFilter`, `@AfterFilter`支持的参数如下：

    A. int order() default 50; //过滤器顺序，越小越靠前执行 
    B. RequestMethod[] method() default {}; //适用的HTTP方法，支持多个方法
    C. String[] value() default {}; //适用的控制器方法名称
 
另外：index/show等无损操作方法应该在方法开始/结束输出DEBUG级别的日志
   
   
### 5.2.2 获取单个资源

  URL入口为： GET /api/$models/$id 

  应该被映射为 `public $Model show(@PathVariable ANY identify)`, 其中identify的type(ANY)和取名应该与具体业务模型相关。
  
  初始的show控制器方法实现如下：
  
```java
    /**
     * <h2>查看特定账户</h2>
     * <p/>
     * GET /admin/api/accounts/{sn}
     */
    @RequestMapping("{sn}")
    public Account show(@PathVariable(value = "sn") String sn) {
        logger.debug("Viewing {}", sn);
        Account account = accountService.findBySn(sn);
        if( account == null ) 
          throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the account with sn = " + sn);
        logger.debug("Viewed  {}", account);
        return account;
    }
```  

  由于update/destroy等方法均需要查询相应的模型实例，所以，可以应用控制器的Fiter Interceptor减少代码重复。

```java
    Account currentAccount;
    
    @BeforeFilter({"show", "update","destroy"})
    public void initCurrentAccount(@PathVariable("sn") String sn){
        logger.debug("Finding account {}", sn);
        currentAccount = accountService.findBySn(sn);
        if( currentAccount == null ) 
          throw new WebClientSideException(HttpStatus.NOT_FOUND, "Can't find the account with sn = " + sn);
    }  
```
 
  代码随后可以被优化为：
  
```java
    @RequestMapping("{sn}")
    public Account show() {
        logger.debug("Viewed  {}", currentAccount);
        return currentAccount;
    }
```

  请注意：
  
  **show方法中sn参数可以被省略，但show方法的`@RequestMapping`中的`PathVariable` `{sn}`不能被省略**

### 5.2.3 创建单个资源

  URL入口为： POST /api/$models

  应该被映射为 `public $Model create($Model instance)`.
  
示例：

```java
    @RequestMapping(method = RequestMethod.POST)
    public Account create(@RequestBody @Valid Account account){
        logger.info("Creating {}", account.getName());
        // 可能会抛出重名的异常
        try{
          currentAccount = accountService.create(account);
        }catch(ServiceException ex){
          throw new WebServerSideException(HttpStatus.SERVER_INTERNAL_ERROR, ex.getMessage());
        }
        logger.info("Created  {} with sn {}", currentAccount.getName(), currentAccount.getSn());
        return currentAccount;
    }
```
备注：

  1. Account对象应该通过请求体以JSON数据格式传入
  2. 输入的Account对象可以基于JSR-161规定的Validator API中的 `@Valid` 进行校验
  3. 创建之前和之后应该使用INFO级别记录日志
  4. 如果service层有抛出异常，根据责任归属，应该将异常转换为web层异常 `WebClientSideException`或`WebServerSideException`，并设置合适的HTTP STATUS CODE。
  5. 由于`ApplicationController` 会自动捕捉所有控制器抛出的异常，并给予合适的输出，所以，控制器方法中可以直接抛出这两个Runtime Exception，也可以抛出其他Runtime Exception（不建议，也不拒绝），不允许抛出Checked Exception；
  之所以不拒绝控制器方法抛出Runtime Exception，这是因为难以避免控制器所调用的方法抛出该异常；之所以不建议，这是建议用户不要主动抛出WebException之外的RuntimeException。
  虽然控制器会handle所有的Throwable，但不允许用户在控制器方法上抛出Checked Exception，尤其是Service Exception，这是为了让WEB开发者养成良好的使用服务层的API的习惯。

### 5.2.4 修改单个资源

  URL入口为： PUT /api/$models/$identify

  应该被映射为 `public $Model update($Model instance)`.

基于前述show实现时已经定义过的BeforeFilter（初始化了currentAccount成员变量）update示例如下：
  
```java
    @RequestMapping( value = "{sn}", method = RequestMethod.PUT)
    public Account update(@RequestBody @Valid Account account){
        logger.info("Updating {}", currentAccount.getSn());
        currentAccount.apply(account);
        Account updated;
        try {
            updated = accountService.update(currentAccount);
        } catch (AccountException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.info("Updated  {}", updated);
        return updated;
    }
```  

备注： update 方法需要注意的问题除了与create相似之外，与show也类似，另外还要注意：
  
  1. 不应该将输入的对象直接传入service层，而是基于已经查出来的对象，用传入对象更新已有对象
  2. 虽然被更新的对象的sn在request body信息中也存在，但是为了保持一致的url规格（这样易于进行拦截，记录，日志，审计，复用），update的url中的path variable `sn`不能省略。
  
### 5.2.5 删除单个资源

  URL入口为： DELETE /api/$models/$identify

  应该被映射为 `public void destroy()`.

  基于前述show实现时已经定义过的BeforeFilter（初始化了currentAccount成员变量）destroy示例如下：
  
```java
    @RequestMapping(value = "{sn}", method = RequestMethod.DELETE)
    public void destroy(){
        logger.warn("Deleting {}", currentAccount.getSn());
        try {
            accountService.delete(currentAccount);
        } catch (AccountException e) {
            throw new WebClientSideException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
        logger.warn("Deleted  {}", currentAccount);
    }
```

备注：

  1. 一般service层的delete接口传入实例，而不是实例的identify
  2. destroy的日志级别应该写为WARN
  3. destroy 可以不返回原数据对象
  
--
  
## 5.3 非标准控制器开发

  所谓非标准控制器，是指其并不是服务于特定业务模型，依赖相应业务接口的特殊控制器
 
  如，基于用户信息，前端界面可能会要求用户输入login信息，修改密码信息等，这些信息都是user对象的一个局部，也可能包括额外的信息。
这些SPI的开发，需要开发额外的 *模型* , 这些模型没有实际的存储，仅在WEB层使用，我们称之为 *WEB模型* ，一般存放于 `dnt.itsnow.web.model`


  以当前用户的密码管理为例，用户可能有忘记密码，修改密码等典型场景相关需求，其WEB模型分别为
  
```java
package dnt.itsnow.web.model;

/**
 * 修改密码的请求
 */
public class ChangePasswordRequest {
    @NotBlank
    private String oldPassword;
    @NotBlank
    @Length(min = 6, max = 20)
    private String newPassword;
    @NotBlank
    @Repeat
    private String repeatPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
```  
  
```java
package dnt.itsnow.web.model;
/**
 * <h1>忘记密码的请求</h1>
 */
public class ForgetPasswordRequest {
    private String email;
    private String phone;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
```

相应的控制器开发与5.2所述的典型控制器开发一致，只是其SPI接口可能并不是5个典型接口（但应该可以被mapping成为某个类似接口）。
  
  