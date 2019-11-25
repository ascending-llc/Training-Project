package com.ascending.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    /**
     * Dependency Injection method 1
     * @see com.ascending.training.service.FileService#amazonS3
     * @Autowired
     * private AmazonS3 amazonS3;
     */

    /**
     * Dependency Injection method 2
     */
    private AmazonSQS amazonSQS;

    private String queueUrl;

    public MessageService(@Autowired AmazonSQS sqsClient,@Value("${aws.queue.name}") String queueName){
        this.amazonSQS = sqsClient;
        this.queueUrl = getQueueUrl(queueName);
    }


    public String createQueue(String queueName) {
        try {
            return getQueueUrl(queueName);
        }
        catch (QueueDoesNotExistException e) {
            CreateQueueRequest createStandardQueueRequest = new CreateQueueRequest(queueName);
            return amazonSQS.createQueue(createStandardQueueRequest).getQueueUrl();
        }
    }

    public String getQueueUrl(String queueName) {
        return amazonSQS.getQueueUrl(queueName).getQueueUrl();
    }


    public void sendMessage(Map<String, MessageAttributeValue>  messagebody, Integer delaySec){
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageAttributes(messagebody)
                .withDelaySeconds(delaySec);
        amazonSQS.sendMessage(sendMsgRequest);
    }

    public void sendMessage(String  messagebody, Integer delaySec){
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(messagebody)
                .withDelaySeconds(delaySec);
        amazonSQS.sendMessage(sendMsgRequest);
    }


//    public void sendMessage(String queueName, String msg) {
//        Map<String, MessageAttributeValue> messageAttributes = new HashMap<>();
//        MessageAttributeValue messageAttributeValue = new MessageAttributeValue();
//        messageAttributeValue.withDataType("String")
//                             .withStringValue("File URL in S3");
//        messageAttributes.put("message", messageAttributeValue);
//        String queueUrl = getQueueUrl(queueName);
//        SendMessageRequest sendMessageStandardQueue = new SendMessageRequest();
//        sendMessageStandardQueue.withQueueUrl(queueUrl)
//                                .withMessageBody(msg)
//                                .withDelaySeconds(10)
//                                .withMessageAttributes(messageAttributes);
//        amazonSQS.sendMessage(sendMessageStandardQueue);
//    }
}
