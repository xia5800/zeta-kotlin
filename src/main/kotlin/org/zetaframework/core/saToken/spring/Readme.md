# 说明

暂时解决sa-token使用PathPattern匹配路由导致的`No more pattern data allowed after {*...} or ** pattern element`问题

原理是使用BeanDefinitionRegistryPostProcessor替换了SaTokenContextRegister中定义的Bean