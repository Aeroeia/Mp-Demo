# MyBatis-Plus 学习项目

这是一个用于学习和练习 MyBatis-Plus (MP) 功能的示例项目。MyBatis-Plus 是一个 MyBatis 的增强工具，提供了许多强大的功能扩展。

## 项目结构

主要代码位于以下位置：

- `src/test/java/com/itheima/mp/MpDemoApplicationTests.java` - 测试用例
- `src/main/java/com/itheima/mp/mapper` - 数据访问层接口
- `src/main/java/com/itheima/mp/Service` - 业务逻辑层

## 核心接口学习

本项目主要聚焦于 MyBatis-Plus 的两个核心接口：

### BaseMapper<T>

BaseMapper 是 MyBatis-Plus 提供的基础 Mapper 接口，只需要我们自定义的 Mapper 接口继承它，就可以获得许多开箱即用的方法：

```java
public interface UserMapper extends BaseMapper<User> {
    // 自定义方法...
}
```

BaseMapper 提供的常用方法：

- `insert(T entity)` - 插入一条记录
- `deleteById(Serializable id)` - 根据 ID 删除
- `delete(Wrapper<T> queryWrapper)` - 根据条件删除
- `updateById(T entity)` - 根据 ID 更新
- `update(T entity, Wrapper<T> updateWrapper)` - 根据条件更新
- `selectById(Serializable id)` - 根据 ID 查询
- `selectList(Wrapper<T> queryWrapper)` - 根据条件查询所有
- `selectCount(Wrapper<T> queryWrapper)` - 根据条件查询总记录数

### IService<T>

IService 接口提供了比 BaseMapper 更丰富的方法，是对 BaseMapper 的进一步封装和扩展：

```java
public interface IUserService extends IService<User> {
    // 自定义方法...
}

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    // 实现自定义方法...
}
```

IService 提供的常用方法：

- `save(T entity)` - 插入一条记录
- `saveBatch(Collection<T> entityList)` - 批量插入
- `removeById(Serializable id)` - 根据 ID 删除
- `update(T entity, Wrapper<T> updateWrapper)` - 根据条件更新
- `getById(Serializable id)` - 根据 ID 查询
- `list(Wrapper<T> queryWrapper)` - 根据条件查询所有
- `page(Page<T> page, Wrapper<T> queryWrapper)` - 分页查询

## 功能示例

本项目包含以下 MyBatis-Plus 功能的示例：

1. **基本 CRUD 操作**：基础的增删改查功能
2. **条件构造器**：使用 QueryWrapper、LambdaQueryWrapper、LambdaUpdateWrapper 等
3. **自定义 SQL**：自定义 SQL 语句的使用方法
4. **分页查询**：使用 MP 的分页插件进行分页查询
5. **逻辑删除**：实现数据的逻辑删除功能
6. **乐观锁**：通过版本控制实现乐观锁
7. **枚举类型处理**：枚举类型的处理和转换
8. **结合 Hutool 工具包**：与 Hutool 工具的集成使用
9. **分页排序**：结合 OrderItem 实现分页排序
10. **VO对象转换**：实体对象与视图对象的转换

## 核心依赖

- Spring Boot
- MyBatis-Plus
- Hutool 工具包
- Lombok

## 学习资源

- [MyBatis-Plus 官方文档](https://baomidou.com/)
- [Hutool 官方文档](https://www.hutool.cn/docs/)
- [Spring Boot 官方文档](https://spring.io/projects/spring-boot) 