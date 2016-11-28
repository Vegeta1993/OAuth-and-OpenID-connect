# OAuth-and-OpenID-connect
A java web application based on maven to authenticate and authorize user using OAuth and OpenID connect.

This project is done to learn OAuth of Facebook and OpenID connect of Google. Here Session fixation is addressed and to mitigate XSS have introduced OWASP encode library. For implementing OpenID connect have used NimbusDS library.

In both OAuth and OpenID connect protocol, I have just requested username and email ID. But if you want to take it for a spin, you can use various Google API and Facebook API's for liking, commenting etc.

The client secret is no longer in use, so request for new one in https://console.developers.google.com and https://developers.facebook.com.

You might want to put it on remote server with no DNS server, and some authentication provider might not accept this. Then you can use xip.io or nip.io which will redirect to your URL.

As I only tried this on HTTP server, my web application is vulnerable to session hijacking as it doesn't use TLS/SSL.

When you download/clone this project you might find a lot of errors. So add this to your POM file.

<properties>
    <failOnMissingWebXml>false</failOnMissingWebXml>
</properties>

And set your targeted runtimes and deployment assembly and project facets. 
