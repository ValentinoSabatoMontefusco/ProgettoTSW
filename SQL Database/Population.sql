-- INSERT INTO users (username, email, password, create_time)

INSERT INTO users (username, password, role, create_time)
VALUES 
("admin", "d7f830a94900f25e4985d51ca317308735cf8129405c834d3ebfe899e03989946517b519fe7c44201ee09b7d2cbe24a10448ee084395102e4d445f3b5ad7d0a4", "admin", "2021-12-25 12:00:00"),
("frontend_guy", "b64385f13633f9bf4627e0ed72952c88a645836a3f6ac4ff4d65efea3c31d07111ba4d6c3295ae33b842511d69256a69dc4f7408234359873c7d3f4de8e2d730", "user", "2023-04-22 19:32:11"),
("gotoRevival", "140d3cc42d270d0fca36c67192148622c0240cb0b1871263183387431fd6f4c47e32056d204b8346d4a30b07e40b1933f83887e940a1f2e65181a5f226cd6082", "user", "2023-05-10 14:11:26"),
("filler_feller", "1993c859495f0296a327d45c2e17e294cf8e0fc885b7192770d18d021c1f5f905fa2532f5d126331d6cb8a80bd102852c3bb869ccf7fa19904c8095de777361d", "user", "2023-02-14 10:10:10"),
("moneyfornothing", "8edbc686fc026da3867b26228fc980019e7b1bc6780f6ce437147c8202dad1a48eeb61358c7cef2a5afa9062c0bbf64e392a66342c1bb82776f088bc3f0da613", "user", "2023-06-27 8:15:00");


INSERT INTO products (name, description, type, price)
VALUES
('Ajax', 'Ajax stands for Asynchronous JavaScript and XML. It is a web development technique that enables the asynchronous exchange of data between a web browser and a server without the need for a page refresh. By using a combination of JavaScript and various technologies like XML or JSON, Ajax allows developers to create dynamic and interactive web applications. It enhances the user experience by enabling seamless updates to specific sections of a web page, making applications feel more responsive.', 'Course', '14.99'),
('Java', 'Java is a widely-used general-purpose programming language known for its portability, security, and scalability. It follows the &quotwrite once, run anywhere&quot principle, meaning that Java code can run on any platform that has a Java Virtual Machine (JVM) installed. Java is object-oriented, has a large standard library, and is commonly used for developing enterprise-level applications, Android apps, and server-side software. It provides robust features such as automatic memory management, exception handling, and multithreading, making it suitable for building complex and scalable systems.', 'Course', '24.99'),
('Javascript', 'JavaScript is a versatile programming language primarily used for web development. It runs on the client-side, allowing developers to add interactivity and dynamic behavior to web pages. JavaScript enables functions like form validation, interactive maps, and dynamic content updates. It has a wide range of frameworks and libraries, such as React, Angular, and Vue.js, which extend its capabilities for building complex web applications. JavaScript is supported by all modern web browsers and has evolved to be used on the server-side as well (Node.js), enabling full-stack web development.', 'Course', '19.99'),
('CSS', 'CSS, or Cascading Style Sheets, is a styling language used to describe the visual presentation of a document written in HTML or XML. It allows developers to control the layout, colors, fonts, and other visual aspects of web pages. CSS uses a set of rules to target specific elements on a web page and apply styling properties to them. It provides flexibility and separation of concerns, allowing developers to separate the structure (HTML) and presentation (CSS) of a web page, making it easier to maintain and update the design across multiple pages or an entire website.', 'Course', '19.99'),
('XML', 'XML, or Extensible Markup Language, is a markup language designed to store and transport data. It uses tags to define elements and their hierarchical relationships, allowing structured representation of information. XML is human-readable and can be easily parsed by both humans and machines. It is commonly used for data exchange between different systems and platforms. XML is also the foundation of other technologies such as XHTML, SOAP, and RSS, and it plays a significant role in defining document structures and data formats.', 'Course', '14.99'),
("JSON", 'JSON, or JavaScript Object Notation, is a lightweight data interchange format that is easy for humans to read and write, and for machines to parse and generate. It is based on a subset of JavaScript syntax and is often used to transmit data between a server and a web application as an alternative to XML. JSON provides a simple and compact way to represent structured data, making it popular for web APIs and data storage. It supports various data types, including strings, numbers, booleans, arrays, and objects, making it a versatile format for data exchange and serialization.', 'Course', "12.99"),
("JQuery", 'jQuery is a fast, small, and feature-rich JavaScript library. It simplifies HTML document traversal, event handling, animation, and provides an easy-to-use API for manipulating the Document Object Model (DOM). jQuery helps developers write concise and efficient JavaScript code, abstracting many cross-browser compatibility issues and providing a unified interface for common web development tasks. It simplifies AJAX calls, DOM manipulation, and event handling, allowing developers to create interactive web applications more easily.', 'Course', "9.99"),
("SQL", 'SQL, or Structured Query Language, is a programming language used for managing and manipulating relational databases. It provides a standardized way to interact with databases, allowing users to define, retrieve, and manipulate data stored in tables. SQL supports operations such as querying, inserting, updating, and deleting data. It is widely used in web development, enterprise systems, and data analysis. SQL offers powerful features like joins, aggregations, and transactions, enabling complex data manipulations and ensuring data integrity. Popular database management systems like MySQL, Oracle, and PostgreSQL support SQL as the primary language for interacting with databases.','Course', "24.99"),
("JSP", 'JSP, or JavaServer Pages, is a technology used for creating dynamic web pages with Java. It allows developers to embed Java code within HTML pages, making it easy to generate dynamic content based on data or user input. JSP pages are compiled into Java servlets that run on the server-side and generate the resulting HTML content. JSP combines the power of Java with the simplicity of HTML, enabling developers to create feature-rich and interactive web applications. It is often used together with Java Servlets, JavaBeans, and other Java technologies to build enterprise-level web applications.','Course', "14.99"),
("HTML", "HTML, or Hypertext Markup Language, is the standard markup language for creating web pages and web applications. It provides the structure and content of a webpage by using a set of tags and attributes that define various elements such as headings, paragraphs, images, links, forms, and more. HTML is the backbone of the web, serving as the building blocks for websites. It is a static language, meaning that it describes the structure and content of a web page but doesn't provide interactivity on its own. To enhance interactivity and styling, HTML is often combined with CSS and JavaScript.",'Course', "19.99");


INSERT INTO products (name, description, type, price)
VALUES
('Web Coding and Development All in One For Dummies', "Speak the languages that power the web With more high-paying web development jobs opening every day, people with coding and web/app building skills are having no problems finding employment. If you're a would-be developer looking to gain the know-how to build the interfaces, databases, and other features that run modern websites, web apps, and mobile apps, look no further. Web Coding & Development All-in-One For Dummies is your go-to interpreter for speaking the languages that handle those tasks. Get started with a refresher on the rules of coding before diving into the languages that build interfaces, add interactivity to the web, or store and deliver data to sites. When you're ready, jump into guidance on how to put it all together to build a site or create an app. Get the lowdown on coding basics Review HTML and CSS Make sense of JavaScript, jQuery, PHP, and MySQL Create code for web and mobile apps There's a whole world of opportunity out there for developers-and this fast-track boot camp is here to help you acquire the skills you need to take your career to new heights!",
'Merchandise', '44.99');

INSERT INTO merchandise (product_id, amount)
SELECT id, 5 FROM products WHERE type = 'Merchandise';

INSERT INTO courses (product_id)
(SELECT id FROM products WHERE type = "Course");

INSERT INTO lessons (course_id, title, content)
SELECT id, "Richieste asincrone", "Poi vedremo... ;)"
FROM products WHERE name = 'Ajax';

INSERT INTO lessons (course_id, title, content)
VALUES
(1, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(1, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(1, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(1, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(2, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(2, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(2, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(2, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(2, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(3, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(3, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(3, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(4, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(4, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(4, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(4, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(5, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(5, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(5, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(5, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(5, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(6, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(6, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(6, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(6, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(7, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(7, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(7, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(8, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(8, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(8, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(8, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(9, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(9, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(9, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(10, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(10, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(10, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(10, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100))),
(10, CONCAT('Random Title ', FLOOR(RAND() * 100)), CONCAT('Random Content ', FLOOR(RAND() * 100)));






INSERT INTO orders (user_username, order_date)
VALUES 
("frontend_guy", '2023-05-12 09:37:21'),
("gotoRevival", '2023-06-05 15:21:46'),
("moneyfornothing", '2023-06-10 14:30:58'),
("moneyfornothing", '2023-06-10 14:41:22'),
("moneyfornothing",  '2023-06-18 08:12:59'),
("filler_feller", '2023-06-28 06:59:12'),
("frontend_guy", '2023-07-02 23:45:33' );

INSERT INTO order_products  (order_id, product_id, product_name, product_type, product_price, quantity)
VALUES 
(1, 3, "JavaScript", "Course", 19.99, 1),
(1, 4, "CSS", "Course", 19.99, 1),
(1, 10, "HTML", "Course", 19.99, 1),
(2, 2, "Java", "Course", 24.99, 1),
(2, 9, "JSP", "Course", 19.99, 1), 
(3, 11, "Web Coding and Development All in One For Dummies", "Merchandise", 39.99, 1),
(3, 10, "HTML", "Course", 19.99, 1),
(3, 2, "Java", "Course", 24.99, 1),
(4, 11, "Web Coding and Development All in One For Dummies", "Merchandise", 39.99, 2),
(4, 1, "Ajax", "Course", 14.99, 1),
(4, 3, "JavaScript", "Course", 19.99, 1),
(4, 5, "XML", "Course", 14.99, 1),
(5, 11, "Web Coding and Development All in One For Dummies", "Merchandise", 44.99, 4),
(5, 9, "JSP", "Course", 19.99, 1),
(6, 6, "JSON", "Course", 12.99, 1),
(7, 7, "JQuery", "Course", 9.99, 1),
(7, 11, "Web Coding and Development All in One For Dummies", "Merchandise", 44.99, 1);

