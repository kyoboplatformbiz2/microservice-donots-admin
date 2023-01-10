package com.kyobo.platform.donots.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.core.io.Resource;

@Slf4j
@Configuration
public class SSHConfig {
    //@Value("#{system[bastion_ec2]}")
    private String bastion_ec2 = "ec2-54-180-30-131.ap-northeast-2.compute.amazonaws.com";
    //@Value("#{system[rds_port]}")
    private String rds_port = "5432";
    //@Value("#{system[ec2_username]}")
    private String ec2_username = "ec2-user";
    //@Value("#{system[rds_endpoint]}")
    private String rds_endpoint = "account-db.crfubebnxuuv.ap-northeast-2.rds.amazonaws.com";
    //@Value("#{system[pem_key_path]}")
    private String pem_key_path = "src/main/resources/postgre-rds-key.pem";
    private static JSch jsch = new JSch();
    private Session session;
    private int forward_port = 0;
    private int ssh_port = 22;  //ssh포트
    private int lport = 0;  //원격 접속 후 가상으로 포워딩할 포트


    public int init() {
        try {

            log.info(bastion_ec2);
            log.info(rds_port);
                    log.info(ec2_username);
            log.info(rds_endpoint);
            log.info(pem_key_path);

            ClassPathResource classPathResource = new ClassPathResource(pem_key_path);

            //jsch.addIdentity(classPathResource.getPath()); // 개인키
            jsch.addIdentity("postgre-rds-key.pem", readPemResource().getBytes(), null,null);
            session = jsch.getSession(ec2_username, bastion_ec2, ssh_port);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            forward_port = session.setPortForwardingL(lport, rds_endpoint, Integer.parseInt(rds_port));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return forward_port;
    }

    public void shutdown() throws Exception {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }
    }

    private String readPemResource() {
        String builtResource;
        Resource newResource = new ClassPathResource("postgre-rds-key.pem");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(newResource.getInputStream()))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append('\n');
            }
            builtResource = builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return builtResource;
    }
}
