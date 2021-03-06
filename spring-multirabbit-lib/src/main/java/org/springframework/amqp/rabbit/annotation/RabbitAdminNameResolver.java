package org.springframework.amqp.rabbit.annotation;

import org.springframework.boot.autoconfigure.amqp.MultiRabbitConstants;

import static org.springframework.util.StringUtils.hasText;

final class RabbitAdminNameResolver
{

    private RabbitAdminNameResolver()
    {
    }


    /**
     * Resolves the name of the RabbitAdmin bean based on the RabbitListener.
     *
     * @param rabbitListener The RabbitListener to process the name from.
     * @return The name of the RabbitAdmin bean.
     */
    static String resolve(RabbitListener rabbitListener)
    {
        String bean = rabbitListener.admin();
        if (!hasText(bean) && hasText(rabbitListener.containerFactory()))
        {
            bean = rabbitListener.containerFactory() + MultiRabbitConstants.RABBIT_ADMIN_SUFFIX;
        }
        if (!hasText(bean))
        {
            bean = MultiRabbitConstants.DEFAULT_RABBIT_ADMIN_BEAN_NAME;
        }
        return bean;
    }

}
