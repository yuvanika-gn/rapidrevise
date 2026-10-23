-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Mar 30, 2026 at 08:58 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rapidrevise`
--

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `subject_name` varchar(100) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `notes`
--

INSERT INTO `notes` (`id`, `user_id`, `subject_name`, `title`, `content`, `created_at`) VALUES
(3, 2, 'Data structures', 'DSA', 'Concepts', '2026-02-17 07:36:58'),
(4, 2, 'Computer architecture', 'Computer architecture', 'Short notes', '2026-02-17 16:57:44'),
(5, 2, 'Computer architecture', 'Lorem ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.', '2026-02-17 16:58:41'),
(8, 1, 'Computer science', 'Data structures', 'Here’s a  clear and simple overview of Data Structures (perfect for revision, interviews, and your app notes).\n\n What is a Data Structure?\n\nA **Data Structure** is a way of organizing and storing data so it can be used efficiently.\n\n👉 Think of it like:\n\n* 📁 Folder → organizes files\n* 🧺 Basket → holds items in a structured way\nTypes of Data Structures\n1. Primitive Data Structures\n\nBasic data types:\n\n* Integer\n* Float\n* Character\n* Boolean\n\n---\n\n### 2. Non-Primitive Data Structures\n\n#### A. Linear Data Structures\n\nData is arranged sequentially.\n\n1. **Array**\n\n   * Fixed size\n   * Fast access using index\n   * Example: `int arr[5]`\n\n2. **Linked List**\n\n   * Dynamic size\n   * Nodes connected using pointers\n   * Types:\n\n     * Singly\n     * Doubly\n     * Circular\n\n3. **Stack (LIFO)**\n\n   * Last In First Out\n   * Operations:\n\n     * Push\n     * Pop\n   * Example: Undo operation\n\n4. **Queue (FIFO)**\n\n   * First In First Out\n   * Operations:\n\n     * Enqueue\n     * Dequeue\n   * Example: Waiting line\n\n---\n\n#### B. Non-Linear Data Structures\n\nData is not in sequence.\n\n1. **Tree**\n\n   * Hierarchical structure\n   * Example: File system\n   * Types:\n\n     * Binary Tree\n     * BST\n     * AVL Tree\n\n2. **Graph**\n\n   * Set of nodes connected by edges\n   * Used in maps, networks\n\n---\n\n## 🔹 Important Operations\n\n* Insertion\n* Deletion\n* Traversal\n* Searching\n* Sorting\n\n---\n\n## 🔹 Time Complexity (Basic Idea)\n\nMeasures efficiency of algorithms.\n\n* **O(1)** → Constant\n* **O(n)** → Linear\n* **O(log n)** → Logarithmic\n* **O(n²)** → Quadratic\n\n---\n\n## 🔹 Why Data Structures Matter\n\n* Improves performance\n* Saves memory\n* Helps solve complex problems efficiently\n* Important for **placements + coding interviews**\n\n---\n\n## 🔹 Real-Life Examples\n\n* Stack → Browser back button\n* Queue → Ticket booking system\n* Tree → Folder structure\n* Graph → Google Maps\n\n---\n\n## 🔹 Quick Summary\n\n* Data structures = organizing data efficiently\n* Two main types → Linear & Non-linear\n* Key ones → Array, Linked List, Stack, Queue, Tree, Graph\n\n---\n\nIf you want, I can:\n\n* Give **C / Java / Python code for each DS**\n* Create **interview questions**\n* Or format this for your **Rapid Revise app UI** (title + notes format)', '2026-03-01 15:10:34'),
(9, 1, 'Data science', 'Hello sir I am waiting', 'Helooo and I will be okay if I made a', '2026-03-10 08:09:52'),
(10, 1, 'Design', 'Design', 'Designer dresses and skirts for women day', '2026-03-12 06:11:38'),
(11, 1, 'Computer architecture', 'Data entry', 'Random notes', '2026-03-16 07:26:50'),
(12, 1, 'App development', 'App development', 'Android studio', '2026-03-16 07:30:05'),
(13, 1, 'Design', 'User-Centered Design', 'User-Centered Design focuses on designing products based on the needs, behaviors, and feedback of users. It involves continuous user testing, feedback loops, and improvements to ensure the final product is intuitive and easy to use', '2026-03-21 11:51:16'),
(14, 1, 'Design', 'Principles of Good Design', 'Good design follows principles like simplicity, consistency, clarity, and accessibility. A well-designed interface minimizes user effort and enhances usability while maintaining visual appeal.', '2026-03-21 11:51:56'),
(15, 1, 'Design', 'UI (User Interface) Design', 'UI design deals with the visual elements of an application such as buttons, colors, typography, and layout. The goal is to create an attractive and interactive interface that enhances user engagement.', '2026-03-21 11:52:32'),
(16, 1, 'Design', 'UX (User Experience)', 'UX design focuses on the overall experience of the user while interacting with a product. It includes usability, functionality, and user satisfaction, ensuring the product is efficient and enjoyable.', '2026-03-21 11:52:59'),
(17, 1, 'Design', 'Design Thinking Process', 'Design Thinking is a problem-solving approach that includes five stages: Empathize, Define, Ideate, Prototype, and Test. It helps designers create innovative and user-focused solution', '2026-03-21 11:53:31'),
(18, 1, 'Design', 'Wireframing and Prototyping', 'Wireframes are basic layouts that outline the structure of a design, while prototypes are interactive models used to test functionality before development', '2026-03-21 11:53:48'),
(19, 1, 'Design', 'Responsive Design', 'Responsive design ensures that applications work smoothly across different devices and screen sizes, such as mobile phones, tablets, and desktops.', '2026-03-21 11:54:09'),
(20, 1, 'Design', 'Visual Hierarchy in Design', 'Visual hierarchy guides users\' attention using size, color, contrast, and spacing. It helps users easily navigate and understand the content.', '2026-03-21 11:54:29'),
(21, 1, 'Design', 'Color Theory in Design', 'Color theory involves choosing colors that enhance readability and evoke emotions. Proper color combinations improve user engagement and accessibility.', '2026-03-21 11:54:49'),
(22, 1, 'Design', 'Typography in Design', 'Typography focuses on font style, size, spacing, and readability. Good typography ensures clear communication and enhances the overall design aesthetics.', '2026-03-21 11:55:07'),
(23, 1, 'Design', 'User centeric design', 'User-Centered Design focuses on designing products based on the needs, behaviors, and feedback of users. It involves continuous user testing, feedback loops, and improvements to ensure the final product is intuitive and easy to use.', '2026-03-21 11:56:10'),
(24, 1, 'Sample', 'Sample Page', 'Lorem ipsum', '2026-03-21 12:10:50'),
(25, 1, 'Data science', 'User-Centered Design', 'of ChatGPT.\nWhich response do you prefer? Responses may take a moment to load.\nChatGPT\nResponse 1\n\nHere are some clear, presentation-ready titles and short notes you can use for Design (especially useful for your UI/UX or app-related work):\n\n1. Title: User-Centered Design (UCD)\n\nNotes:\nUser-Centered Design focuses on designing products based on the needs, behaviors, and feedback of users. It involves continuous user testing, feedback loops, and improvements to ensure the final product is intuitive and easy to use.', '2026-03-21 12:11:43'),
(26, 1, 'Data science', 'Principles of Good Design', 'of ChatGPT.\nWhich response do you prefer? Responses may take a moment to load.\nChatGPT\nResponse 1\n\nHere are some clear, presentation-ready titles and short notes you can use for Design (especially useful for your UI/UX or app-related work):\n\n1. Title: User-Centered Design (UCD)\n\nNotes:\nUser-Centered Design focuses on designing products based on the needs, behaviors, and feedback of users. It involves continuous user testing, feedback loops, and improvements to ensure the final product is intuitive and easy to use.\n\n2. Title: Principles of Good Design\n\nNotes:\nGood design follows principles like simplicity, consistency, clarity, and accessibility. A well-designed interface minimizes user effort and enhances usability while maintaining visual appeal.', '2026-03-21 12:12:11'),
(27, 1, 'App development', 'App development', 'Android studio plays a main role in developing apps it is unknowingly as Great companion for creating apps', '2026-03-21 14:19:32'),
(28, 1, 'Physics', 'Motion', 'Uniform Motion: Equal distances in equal intervals of time\nNon-uniform Motion: Unequal distances in equal intervals\nCircular Motion: Motion in a circular path', '2026-03-22 05:36:51'),
(29, 1, 'Physics', 'Laws of Motion (Newton’s Laws)', 'Physics notes with title and notes\n\nHere are clean, simple Physics notes with proper titles and explanations (useful for exams or quick revision):\n\n1. Motion\n\nhttps://d1e4pidl3fu268.cloudfront.net/d3cadde3-61b3-463c-82c0-c150eb7db5ec/Screenshot_14.crop_854x641_1%2C0.preview.png\n\nhttps://images.openai.com/static-rsc-3/5s6jGEQm2PFsRhddg6acdYd1cjXYG-IR7I2r-pzOTK92kBfFNhT2ivSLN3Ez9aF7fmoKvXVXeL3pJFpezoNLMnH4v7R9grFPVJb6rGgIvKA?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/YSpXS3v8YmApwVgVbQNXfhVVvKjYk_p_j1nWoQqsn0iiGcYf3g3yJfcKFQNEAz9s6enx621eFHw_aa19lmLakYPSpLy8paR1GZuwIEpoZlg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/ZQc7atNaesQ5A_1nKwWIcPGstJxxhj8iFT_E7OMXRBKFpSB306RekCPcuRkagrmxCFH_rzy63JSR6RI7ydllTtqBaXxQHLOOeNox6leFoAU?purpose=fullsize&v=1\nDefinition:\nMotion is the change in position of an object with respect to time.\n\nTypes of Motion:\n\nUniform Motion: Equal distances in equal intervals of time\n\nNon-uniform Motion: Unequal distances in equal intervals\n\nCircular Motion: Motion in a circular path\n\nImportant Terms:\n\nDistance → Total path covered\n\nDisplacement → Shortest path from initial to final position\n\nSpeed → Distance / Time\n\nVelocity → Displacement / Time\n\n2. Laws of Motion (Newton’s Laws)\n\nhttps://images.openai.com/static-rsc-3/NMceFEaS7ij0Hcr6S80b57_V4XpzEhaIsvPwqZpY0PPR9q3JFEXe5XmFmnzSy6vLqgg_78rG_KciyTT7r1A4CNi3SWDvUaScFbndl5LCdYY?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/lI2co2U4g8pls_k7UwYNzFXNmQUsOTZn5lIJGv1d5bZgIUjDVBl6i5j6oc_rOQ05gm0SqiEa5f-QLlVk_-7-2wMHslRHnTZYs7v35Z2veq8?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/VQe1rUW4ZHj2I1qAPdvTK2vc-XhiXDRiRwLcIztF-qs9pCQ3R04sBzf7bZWCeqkvMYO7oFxct_EnO2rP8Dvu834CF3DAon_mRNhAAsDGIo8?purpose=fullsize&v=1\n\nhttps://dr282zn36sxxg.cloudfront.net/datastreams/f-d%3Ada48ee8345b380436026ba2c713ba19d4f7c6d92de80c744ca01bf73%2BIMAGE_THUMB_POSTCARD_TINY%2BIMAGE_THUMB_POSTCARD_TINY.1\n1st Law (Law of Inertia):\nAn object remains at rest or in uniform motion unless acted upon by an external force.\n\n2nd Law:\nForce = Mass × Acceleration\nF = m × a\n\n3rd Law:\nEvery action has an equal and opposite reaction.', '2026-03-22 05:37:18'),
(30, 1, 'Physics', 'Work, Energy, and Power', 'Physics notes with title and notes\n\nHere are clean, simple Physics notes with proper titles and explanations (useful for exams or quick revision):\n\n1. Motion\n\nhttps://d1e4pidl3fu268.cloudfront.net/d3cadde3-61b3-463c-82c0-c150eb7db5ec/Screenshot_14.crop_854x641_1%2C0.preview.png\n\nhttps://images.openai.com/static-rsc-3/5s6jGEQm2PFsRhddg6acdYd1cjXYG-IR7I2r-pzOTK92kBfFNhT2ivSLN3Ez9aF7fmoKvXVXeL3pJFpezoNLMnH4v7R9grFPVJb6rGgIvKA?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/YSpXS3v8YmApwVgVbQNXfhVVvKjYk_p_j1nWoQqsn0iiGcYf3g3yJfcKFQNEAz9s6enx621eFHw_aa19lmLakYPSpLy8paR1GZuwIEpoZlg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/ZQc7atNaesQ5A_1nKwWIcPGstJxxhj8iFT_E7OMXRBKFpSB306RekCPcuRkagrmxCFH_rzy63JSR6RI7ydllTtqBaXxQHLOOeNox6leFoAU?purpose=fullsize&v=1\nDefinition:\nMotion is the change in position of an object with respect to time.\n\nTypes of Motion:\n\nUniform Motion: Equal distances in equal intervals of time\n\nNon-uniform Motion: Unequal distances in equal intervals\n\nCircular Motion: Motion in a circular path\n\nImportant Terms:\n\nDistance → Total path covered\n\nDisplacement → Shortest path from initial to final position\n\nSpeed → Distance / Time\n\nVelocity → Displacement / Time\n\n2. Laws of Motion (Newton’s Laws)\n\nhttps://images.openai.com/static-rsc-3/NMceFEaS7ij0Hcr6S80b57_V4XpzEhaIsvPwqZpY0PPR9q3JFEXe5XmFmnzSy6vLqgg_78rG_KciyTT7r1A4CNi3SWDvUaScFbndl5LCdYY?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/lI2co2U4g8pls_k7UwYNzFXNmQUsOTZn5lIJGv1d5bZgIUjDVBl6i5j6oc_rOQ05gm0SqiEa5f-QLlVk_-7-2wMHslRHnTZYs7v35Z2veq8?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/VQe1rUW4ZHj2I1qAPdvTK2vc-XhiXDRiRwLcIztF-qs9pCQ3R04sBzf7bZWCeqkvMYO7oFxct_EnO2rP8Dvu834CF3DAon_mRNhAAsDGIo8?purpose=fullsize&v=1\n\nhttps://dr282zn36sxxg.cloudfront.net/datastreams/f-d%3Ada48ee8345b380436026ba2c713ba19d4f7c6d92de80c744ca01bf73%2BIMAGE_THUMB_POSTCARD_TINY%2BIMAGE_THUMB_POSTCARD_TINY.1\n1st Law (Law of Inertia):\nAn object remains at rest or in uniform motion unless acted upon by an external force.\n\n2nd Law:\nForce = Mass × Acceleration\nF = m × a\n\n3rd Law:\nEvery action has an equal and opposite reaction.\n\n3. Work, Energy, and Power\n\nhttps://www.monash.edu/__data/assets/image/0011/3868346/Physics-2-Diagram-pushing-crate.png\n\nhttps://images.openai.com/static-rsc-3/aJub6f9frFM11iJgZEi2rSQDpa1Lp16rHVVMqTfyBueOOC-X4ibBbsWSvA3WNrFMtHe50rpebfAnjNJ003BzKiViTy4hZY5UZ9ned25T-_Q?purpose=fullsize&v=1\n\nhttps://www.physics-and-radio-electronics.com/physics/energy/potential-energy/images/gravitational-energy.png\n\nhttps://solarschools.net/build/img/learn/energy/forms/potential//potential-energy-height-diagram_400_resize_q95.jpg\nWork:\nWork is done when force causes displacement\nWork = Force × Distance\n\nEnergy:\nCapacity to do work\n\nTypes of Energy:\n\nKinetic Energy → Energy of motion\n\nPotential Energy → Stored energy\n\nPower:\nRate of doing work\nPower = Work / Time', '2026-03-22 05:37:40'),
(31, 1, 'Physics', 'Gravitational force', 'Physics notes with title and notes\n\nHere are clean, simple Physics notes with proper titles and explanations (useful for exams or quick revision):\n\n1. Motion\n\nhttps://images.openai.com/static-rsc-3/YSpXS3v8YmApwVgVbQNXfhVVvKjYk_p_j1nWoQqsn0iiGcYf3g3yJfcKFQNEAz9s6enx621eFHw_aa19lmLakYPSpLy8paR1GZuwIEpoZlg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/ZQc7atNaesQ5A_1nKwWIcPGstJxxhj8iFT_E7OMXRBKFpSB306RekCPcuRkagrmxCFH_rzy63JSR6RI7ydllTtqBaXxQHLOOeNox6leFoAU?purpose=fullsize&v=1\nDefinition:\nMotion is the change in position of an object with respect to time.\n\nTypes of Motion:\n\nUniform Motion: Equal distances in equal intervals of time\n\nNon-uniform Motion: Unequal distances in equal intervals\n\nCircular Motion: Motion in a circular path\n\nImportant Terms:\n\nDistance → Total path covered\n\nDisplacement → Shortest path from initial to final position\n\nSpeed → Distance / Time\n\nVelocity → Displacement / Time\n\n2. Laws of Motion (Newton’s Laws)\n\nhttps://images.openai.com/static-rsc-3/NMceFEaS7ij0Hcr6S80b57_V4XpzEhaIsvPwqZpY0PPR9q3JFEXe5XmFmnzSy6vLqgg_78rG_KciyTT7r1A4CNi3SWDvUaScFbndl5LCdYY?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/lI2co2U4g8pls_k7UwYNzFXNmQUsOTZn5lIJGv1d5bZgIUjDVBl6i5j6oc_rOQ05gm0SqiEa5f-QLlVk_-7-2wMHslRHnTZYs7v35Z2veq8?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/VQe1rUW4ZHj2I1qAPdvTK2vc-XhiXDRiRwLcIztF-qs9pCQ3R04sBzf7bZWCeqkvMYO7oFxct_EnO2rP8Dvu834CF3DAon_mRNhAAsDGIo8?purpose=fullsize&v=1\n\nhttps://dr282zn36sxxg.cloudfront.net/datastreams/f-d%3Ada48ee8345b380436026ba2c713ba19d4f7c6d92de80c744ca01bf73%2BIMAGE_THUMB_POSTCARD_TINY%2BIMAGE_THUMB_POSTCARD_TINY.1\n1st Law (Law of Inertia):\nAn object remains at rest or in uniform motion unless acted upon by an external force.\n\n2nd Law:\nForce = Mass × Acceleration\nF = m × a\n\n3rd Law:\nEvery action has an equal and opposite reaction.\n\n3. Work, Energy, and Power\n\nhttps://www.monash.edu/__data/assets/image/0011/3868346/Physics-2-Diagram-pushing-crate.png\n\nhttps://images.openai.com/static-rsc-3/aJub6f9frFM11iJgZEi2rSQDpa1Lp16rHVVMqTfyBueOOC-X4ibBbsWSvA3WNrFMtHe50rpebfAnjNJ003BzKiViTy4hZY5UZ9ned25T-_Q?purpose=fullsize&v=1\n\nhttps://www.physics-and-radio-electronics.com/physics/energy/potential-energy/images/gravitational-energy.png\n\nhttps://solarschools.net/build/img/learn/energy/forms/potential//potential-energy-height-diagram_400_resize_q95.jpg\nWork:\nWork is done when force causes displacement\nWork = Force × Distance\n\nEnergy:\nCapacity to do work\n\nTypes of Energy:\n\nKinetic Energy → Energy of motion\n\nPotential Energy → Stored energy\n\nPower:\nRate of doing work\nPower = Work / Time\n\n4. Gravitation\n\nhttps://images.openai.com/static-rsc-3/8YSDQ_tdBI_F-rv4FB4KRdPfqEWReOTmD3PhNSDevZ4XSo1N-NRP8fIhkYbOYoLLNYWCNnHwMI4MetZhUHcTz0jQiQ0yD1llj0f1Za88OVE?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/uK7i1bB2zNbD_uVcUt4t28S3YosiBeD-cFkyNhgJKUsX3IhTmHkrbomFdrP95qZjbwm9sICCdNmOfjAD4XrpjbN4LR2K9FT1Ivp7d87fmjg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/XWOlW2--e4L8MVxRAb6bjHKTNq9rI5VzkQC-a2vY8CE2YtoA5ku3OCo3n19gvay_bbcDa5As3AQCOTtdCBSmm-Bn6PoKYpIyUiQ0NJHi1qg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/WJypCaGYLZ9SH5IVmtnw3m3nHlvcqoAkC-fCsDVKJZbhi9b-qlNAFRN2UfygH2XL2HEgybvVRtKWJIRZa2TdSL9K4LzHXT-ELiKyafu1cPQ?purpose=fullsize&v=1\nDefinition:\nGravitation is the force of attraction between objects.\n\nFormula:\nF = G (m₁m₂ / r²)\n\nKey Points:\n\nWeight = Mass × Gravity\n\nGravity on Earth ≈ 9.8 m/s²\n\nGravity keeps planets in orbit', '2026-03-22 05:38:00'),
(32, 1, 'Physics', 'Light (Optics)', 'Physics notes with title and notes\n\nHere are clean, simple Physics notes with proper titles and explanations (useful for exams or quick revision):\n\n1. Motion\n\nhttps://d1e4pidl3fu268.cloudfront.net/d3cadde3-61b3-463c-82c0-c150eb7db5ec/Screenshot_14.crop_854x641_1%2C0.preview.png\n\nhttps://images.openai.com/static-rsc-3/5s6jGEQm2PFsRhddg6vjvjfufuacdYd1cjXYG-IR7I2r-pzOTK92kBfFNhT2ivSLN3Ez9aF7fmoKvXVXeL3pJFpezoNLMnH4v7R9grFPVJb6rGgIvKA?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/YSpXS3v8YmApwVgVbQNXfhVVvKjYk_p_j1nWoQqsn0iiGcYf3g3yJfcKFQNEAz9s6enx621eFHw_aa19lmLakYPSpLy8paR1GZuwIEpoZlg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/ZQc7atNaesQ5A_1nKwWIcPGstJxxhj8iFT_E7OMXRBKFpSB306RekCPcuRkagrmxCFH_rzy63JSR6RI7ydllTtqBaXxQHLOOeNox6leFoAU?purpose=fullsize&v=1\nDefinition:\nMotion is the change in position of an object with respect to time.\n\nTypes of Motion:\n\nUniform Motion: Equal distances in equal intervals of time\n\nNon-uniform Motion: Unequal distances in equal intervals\n\nCircular Motion: Motion in a circular path\n\nImportant Terms:\n\nDistance → Total path covered\n\nDisplacement → Shortest path from initial to final position\n\nSpeed → Distance / Time\n\nVelocity → Displacement / Time\n\n2. Laws of Motion (Newton’s Laws)\n\nhttps://images.openai.com/static-rsc-3/NMceFEaS7ij0Hcr6S80b57_V4XpzEhaIsvPwqZpY0PPR9q3JFEXe5XmFmnzSy6vLqgg_78rG_KciyTT7r1A4CNi3SWDvUaScFbndl5LCdYY?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/lI2co2U4g8pls_k7UwYNzFXNmQUsOTZn5lIJGv1d5bZgIUjDVBl6i5j6oc_rOQ05gm0SqiEa5f-QLlVk_-7-2wMHslRHnTZYs7v35Z2veq8?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/VQe1rUW4ZHj2I1qAPdvTK2vc-XhiXDRiRwLcIztF-qs9pCQ3R04sBzf7bZWCeqkvMYO7oFxct_EnO2rP8Dvu834CF3DAon_mRNhAAsDGIo8?purpose=fullsize&v=1\n\nhttps://dr282zn36sxxg.cloudfront.net/datastreams/f-d%3Ada48ee8345b380436026ba2c713ba19d4f7c6d92de80c744ca01bf73%2BIMAGE_THUMB_POSTCARD_TINY%2BIMAGE_THUMB_POSTCARD_TINY.1\n1st Law (Law of Inertia):\nAn object remains at rest or in uniform motion unless acted upon by an external force.\n\n2nd Law:\nForce = Mass × Acceleration\nF = m × a\n\n3rd Law:\nEvery action has an equal and opposite reaction.\n\n3. Work, Energy, and Power\n\nhttps://www.monash.edu/__data/assets/image/0011/3868346/Physics-2-Diagram-pushing-crate.png\n\nhttps://images.openai.com/static-rsc-3/aJub6f9frFM11iJgZEi2rSQDpa1Lp16rHVVMqTfyBueOOC-X4ibBbsWSvA3WNrFMtHe50rpebfAnjNJ003BzKiViTy4hZY5UZ9ned25T-_Q?purpose=fullsize&v=1\n\nhttps://www.physics-and-radio-electronics.com/physics/energy/potential-energy/images/gravitational-energy.png\n\nhttps://solarschools.net/build/img/learn/energy/forms/potential//potential-energy-height-diagram_400_resize_q95.jpg\nWork:\nWork is done when force causes displacement\nWork = Force × Distance\n\nEnergy:\nCapacity to do work\n\nTypes of Energy:\n\nKinetic Energy → Energy of motion\n\nPotential Energy → Stored energy\n\nPower:\nRate of doing work\nPower = Work / Time\n\n4. Gravitation\n\nhttps://images.openai.com/static-rsc-3/8YSDQ_tdBI_F-rv4FB4KRdPfqEWReOTmD3PhNSDevZ4XSo1N-NRP8fIhkYbOYoLLNYWCNnHwMI4MetZhUHcTz0jQiQ0yD1llj0f1Za88OVE?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/uK7i1bB2zNbD_uVcUt4t28S3YosiBeD-cFkyNhgJKUsX3IhTmHkrbomFdrP95qZjbwm9sICCdNmOfjAD4XrpjbN4LR2K9FT1Ivp7d87fmjg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/XWOlW2--e4L8MVxRAb6bjHKTNq9rI5VzkQC-a2vY8CE2YtoA5ku3OCo3n19gvay_bbcDa5As3AQCOTtdCBSmm-Bn6PoKYpIyUiQ0NJHi1qg?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/WJypCaGYLZ9SH5IVmtnw3m3nHlvcqoAkC-fCsDVKJZbhi9b-qlNAFRN2UfygH2XL2HEgybvVRtKWJIRZa2TdSL9K4LzHXT-ELiKyafu1cPQ?purpose=fullsize&v=1\nDefinition:\nGravitation is the force of attraction between objects.\n\nFormula:\nF = G (m₁m₂ / r²)\n\nKey Points:\n\nWeight = Mass × Gravity\n\nGravity on Earth ≈ 9.8 m/s²\n\nGravity keeps planets in orbit\n\n5. Light (Optics)\n\nhttps://images.openai.com/static-rsc-3/U-F-5FJJfXIfBmNvPRbHCzxJUU80hjQtlBq8l9pItc6yIrEpJLusRO07EleNsXqf3I2R3TCcHwt-bYQQdayzToJXi4NVaStWw8HR4KQXrNE?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/07glXX706Y4E9s8QF2aB59ZK39Eqk9W6qwCbCP8MnKqoh5Nq61Bmm6XDNUWkf9XyMd1xV9_ZK29W3AshmUyYK9IpBz3FIZO5BnaApy0ouzo?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/B1r1Uz1UZiTB6Rlv-zEea9IwWPvo7bT5-Jkxl2ylOJl0CcZTPl-H1wQMqXryNHRchDfglfddsiiZkQEVwdRZRdoMGBj_TUGHLa8BYeSfNAE?purpose=fullsize&v=1\n\nhttps://images.openai.com/static-rsc-3/gvbqF7aea6x4JoYjHPLijWbrjruW3DY2tsK57ENrBgfibxrRNTuJKgVV5GoBiosXY1Mo94Afr_-PrdmAQwoCveWk-kmsQZRwjrMdU0zQBnI?purpose=fullsize&v=1\nReflection:\nBouncing back of light from a surface\n\nRefraction:\nBending of light when it passes through different mediums\n\nLenses:\n\nConvex → Converges light\n\nConcave → Diverges light', '2026-03-22 05:38:24'),
(34, 3, 'Calculus', 'Mathematics', 'Maths is a very tough subject but I like it', '2026-03-25 14:38:29');

-- --------------------------------------------------------

--
-- Table structure for table `signup`
--

CREATE TABLE `signup` (
  `id` int(11) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `signup`
--

INSERT INTO `signup` (`id`, `fullname`, `email`, `password`) VALUES
(1, 'Yuvanika G N', 'yuvanika@gmail.com', 'mypassword123'),
(2, 'Lekha L', 'lekha@gmail.com', 'lekha04'),
(3, 'Charukesh', 'charukesh@gmail.com', 'charukesh');

-- --------------------------------------------------------

--
-- Table structure for table `student_activity`
--

CREATE TABLE `student_activity` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `subject_id` int(11) DEFAULT NULL,
  `feature_type` varchar(50) DEFAULT NULL,
  `action_type` varchar(50) DEFAULT NULL,
  `time_spent` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student_activity`
--

INSERT INTO `student_activity` (`id`, `user_id`, `subject_id`, `feature_type`, `action_type`, `time_spent`, `created_at`) VALUES
(1, -1, 22, 'quiz', 'open', 5, '2026-03-26 08:22:39'),
(2, -1, 22, 'formula', 'open', 5, '2026-03-26 08:23:53'),
(3, -1, 9, 'quiz', 'open', 5, '2026-03-26 08:24:36'),
(4, -1, 9, 'quiz', 'open', 5, '2026-03-26 08:24:43'),
(5, -1, 9, 'key_concept', 'open', 5, '2026-03-26 08:24:57'),
(6, 1, 19, 'quiz', 'open', 5, '2026-03-26 08:28:57'),
(7, 1, 19, 'quiz', 'open', 20, '2026-03-26 08:28:57'),
(8, 3, 26, 'flashcard', 'open', 5, '2026-03-26 08:29:56'),
(9, 3, 26, 'flashcard', 'open', 10, '2026-03-26 08:29:56'),
(10, 3, 25, 'flashcard', 'open', 10, '2026-03-26 08:29:59'),
(11, 3, 25, 'flashcard', 'open', 5, '2026-03-26 08:29:59'),
(12, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:02'),
(13, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:07'),
(14, 3, 25, 'flashcard', 'next', 10, '2026-03-26 08:30:07'),
(15, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:09'),
(16, 3, 25, 'flashcard', 'next', 10, '2026-03-26 08:30:10'),
(17, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:10'),
(18, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:11'),
(19, 3, 25, 'flashcard', 'next', 10, '2026-03-26 08:30:12'),
(20, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:12'),
(21, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:13'),
(22, 3, 25, 'flashcard', 'view', 10, '2026-03-26 08:30:14'),
(23, 3, 25, 'flashcard', 'next', 10, '2026-03-26 08:30:14'),
(24, 1, 22, 'concept', 'open', 15, '2026-03-26 09:05:05'),
(25, 1, 22, 'key_concept', 'open', 5, '2026-03-26 09:05:05'),
(26, 1, 22, 'formula', 'open', 10, '2026-03-26 09:05:27'),
(27, 1, 22, 'formula', 'open', 5, '2026-03-26 09:05:27'),
(28, 1, 19, 'key_concept', 'open', 5, '2026-03-26 12:08:10'),
(29, 1, 19, 'quiz', 'open', 5, '2026-03-26 12:18:31'),
(30, 1, 19, 'quiz', 'open', 5, '2026-03-26 12:18:31'),
(31, 1, 19, 'quiz', 'correct', 5, '2026-03-26 12:18:39'),
(32, 1, 19, 'quiz', 'wrong', 5, '2026-03-26 12:18:39'),
(33, 1, 19, 'quiz', 'wrong', 5, '2026-03-26 12:18:43'),
(34, 1, 19, 'quiz', 'complete', 5, '2026-03-26 12:18:45'),
(35, 1, 19, 'quiz', 'wrong', 5, '2026-03-26 12:18:45'),
(36, 1, 23, 'flashcard', 'open', 5, '2026-03-26 12:54:34'),
(37, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:38'),
(38, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:42'),
(39, 1, 23, 'flashcard', 'next', 5, '2026-03-26 12:54:42'),
(40, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:43'),
(41, 1, 23, 'flashcard', 'next', 5, '2026-03-26 12:54:44'),
(42, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:44'),
(43, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:44'),
(44, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:45'),
(45, 1, 23, 'flashcard', 'next', 5, '2026-03-26 12:54:45'),
(46, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:46'),
(47, 1, 23, 'flashcard', 'next', 5, '2026-03-26 12:54:46'),
(48, 1, 23, 'flashcard', 'view', 5, '2026-03-26 12:54:46'),
(49, 1, 13, 'quiz', 'open', 5, '2026-03-26 13:00:21'),
(50, 1, 13, 'quiz', 'open', 5, '2026-03-26 13:00:21'),
(51, 1, 23, 'key_concept', 'open', 5, '2026-03-26 14:38:08'),
(52, 1, 19, 'formula', 'open', 5, '2026-03-26 14:38:18'),
(53, 1, 19, 'formula', 'open', 5, '2026-03-26 14:38:18'),
(54, 1, 19, 'quiz', 'open', 5, '2026-03-26 14:38:27'),
(55, 1, 19, 'quiz', 'open', 5, '2026-03-26 14:38:27'),
(56, 1, 19, 'key_concept', 'open', 5, '2026-03-26 14:38:30'),
(57, 1, 21, 'formula', 'open', 5, '2026-03-26 15:02:13'),
(58, 1, 21, 'formula', 'open', 5, '2026-03-26 15:02:13'),
(59, 1, 21, 'key_concept', 'open', 5, '2026-03-26 15:02:16'),
(60, 1, 13, 'key_concept', 'open', 5, '2026-03-26 15:02:21'),
(61, 1, 13, 'key_concept', 'open', 5, '2026-03-26 15:02:54'),
(62, 1, 13, 'key_concept', 'open', 5, '2026-03-26 15:28:31'),
(63, 1, 13, 'flashcard', 'open', 5, '2026-03-26 15:29:12'),
(64, 1, 13, 'flashcard', 'view', 5, '2026-03-26 15:29:16'),
(65, 1, 13, 'flashcard', 'view', 5, '2026-03-26 15:29:18'),
(66, 1, 13, 'flashcard', 'view', 5, '2026-03-26 15:29:20'),
(67, 1, 22, 'key_concept', 'open', 5, '2026-03-26 15:48:12'),
(68, 1, 22, 'flashcard', 'open', 5, '2026-03-26 15:48:31'),
(69, 1, 22, 'flashcard', 'view', 5, '2026-03-26 15:48:37'),
(70, 1, 22, 'flashcard', 'view', 5, '2026-03-26 15:48:45'),
(71, 1, 22, 'key_concept', 'open', 5, '2026-03-26 16:21:11'),
(72, 1, 22, 'formula', 'open', 5, '2026-03-26 16:21:35'),
(73, 1, 22, 'formula', 'open', 5, '2026-03-26 16:21:35'),
(74, 1, 22, 'flashcard', 'open', 5, '2026-03-26 16:21:51'),
(75, 1, 22, 'flashcard', 'open', 5, '2026-03-26 16:22:01'),
(76, 1, 22, 'flashcard', 'open', 5, '2026-03-26 16:22:20'),
(77, 1, 22, 'flashcard', 'view', 5, '2026-03-26 16:22:24'),
(78, 1, 22, 'flashcard', 'view', 5, '2026-03-26 16:22:35'),
(79, 1, 22, 'flashcard', 'shuffle', 5, '2026-03-26 16:22:39'),
(80, 1, 22, 'flashcard', 'view', 5, '2026-03-26 16:22:42'),
(81, 1, 22, 'flashcard', 'shuffle', 5, '2026-03-26 16:22:42'),
(82, 1, 22, 'flashcard', 'view', 5, '2026-03-26 16:22:43'),
(83, 1, 22, 'flashcard', 'shuffle', 5, '2026-03-26 16:22:43'),
(84, 1, 22, 'flashcard', 'restart', 5, '2026-03-26 16:22:45'),
(85, 1, 22, 'quiz', 'open', 5, '2026-03-26 16:22:51'),
(86, 1, 22, 'quiz', 'open', 5, '2026-03-26 16:22:52'),
(87, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:02'),
(88, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:02'),
(89, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:03'),
(90, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:03'),
(91, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:04'),
(92, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:04'),
(93, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:06'),
(94, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:06'),
(95, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:07'),
(96, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:08'),
(97, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:09'),
(98, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:09'),
(99, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:10'),
(100, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:10'),
(101, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:12'),
(102, 1, 22, 'quiz', 'correct', 5, '2026-03-26 16:23:12'),
(103, 1, 22, 'quiz', 'complete', 5, '2026-03-26 16:23:14'),
(104, 1, 22, 'quiz', 'wrong', 5, '2026-03-26 16:23:14'),
(105, 1, 22, 'flashcard', 'open', 5, '2026-03-26 17:48:40'),
(106, 1, 22, 'flashcard', 'view', 5, '2026-03-26 17:48:44'),
(107, 1, 23, 'quiz', 'open', 5, '2026-03-26 17:49:39'),
(108, 1, 23, 'quiz', 'open', 5, '2026-03-26 17:49:39'),
(109, 1, 23, 'flashcard', 'open', 5, '2026-03-26 17:52:26'),
(110, 1, 23, 'flashcard', 'view', 5, '2026-03-26 17:52:28'),
(111, 3, 26, 'key_concept', 'open', 5, '2026-03-27 04:30:59'),
(112, 3, 26, 'key_concept', 'open', 5, '2026-03-27 04:31:40'),
(113, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:32:31'),
(114, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:32:43'),
(115, 1, 22, 'formula', 'open', 5, '2026-03-27 04:32:58'),
(116, 1, 22, 'formula', 'open', 5, '2026-03-27 04:32:58'),
(117, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:33:29'),
(118, 1, 22, 'flashcard', 'open', 5, '2026-03-27 04:33:40'),
(119, 1, 22, 'flashcard', 'view', 5, '2026-03-27 04:33:44'),
(120, 1, 22, 'flashcard', 'view', 5, '2026-03-27 04:33:53'),
(121, 1, 22, 'flashcard', 'view', 5, '2026-03-27 04:33:53'),
(122, 1, 22, 'quiz', 'open', 5, '2026-03-27 04:33:59'),
(123, 1, 22, 'quiz', 'open', 5, '2026-03-27 04:33:59'),
(124, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:09'),
(125, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:09'),
(126, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:11'),
(127, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:11'),
(128, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:11'),
(129, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:12'),
(130, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:12'),
(131, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:13'),
(132, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:13'),
(133, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:14'),
(134, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:15'),
(135, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:15'),
(136, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:16'),
(137, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:16'),
(138, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:16'),
(139, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:16'),
(140, 1, 22, 'quiz', 'wrong', 5, '2026-03-27 04:34:17'),
(141, 1, 22, 'quiz', 'correct', 5, '2026-03-27 04:34:17'),
(142, 1, 22, 'quiz', 'complete', 5, '2026-03-27 04:34:17'),
(143, 1, 27, 'formula', 'open', 5, '2026-03-27 04:34:25'),
(144, 1, 27, 'formula', 'open', 5, '2026-03-27 04:34:25'),
(145, 1, 22, 'formula', 'open', 5, '2026-03-27 04:34:28'),
(146, 1, 22, 'formula', 'open', 5, '2026-03-27 04:34:28'),
(147, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:34:34'),
(148, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:48:54'),
(149, 1, 19, 'flashcard', 'open', 5, '2026-03-27 04:49:12'),
(150, 1, 19, 'flashcard', 'view', 5, '2026-03-27 04:49:15'),
(151, 1, 19, 'flashcard', 'view', 5, '2026-03-27 04:49:17'),
(152, 1, 19, 'flashcard', 'view', 5, '2026-03-27 04:49:19'),
(153, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:49:27'),
(154, 1, 22, 'formula', 'open', 5, '2026-03-27 04:49:37'),
(155, 1, 22, 'formula', 'open', 5, '2026-03-27 04:49:37'),
(156, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:49:49'),
(157, 1, 23, 'formula', 'open', 5, '2026-03-27 04:50:01'),
(158, 1, 23, 'formula', 'open', 5, '2026-03-27 04:50:01'),
(159, 1, 23, 'key_concept', 'open', 5, '2026-03-27 04:50:10'),
(160, 1, 23, 'key_concept', 'open', 5, '2026-03-27 04:50:23'),
(161, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:51:12'),
(162, 1, 22, 'formula', 'open', 5, '2026-03-27 04:52:22'),
(163, 1, 22, 'formula', 'open', 5, '2026-03-27 04:52:22'),
(164, 1, 22, 'flashcard', 'open', 5, '2026-03-27 04:52:45'),
(165, 1, 22, 'flashcard', 'open', 5, '2026-03-27 04:53:26'),
(166, 1, 22, 'flashcard', 'view', 5, '2026-03-27 04:53:34'),
(167, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:53:50'),
(168, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:56:57'),
(169, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:57:14'),
(170, 1, 23, 'key_concept', 'open', 5, '2026-03-27 04:57:20'),
(171, 1, 19, 'key_concept', 'open', 5, '2026-03-27 04:57:38'),
(172, 1, 22, 'formula', 'open', 5, '2026-03-27 04:57:55'),
(173, 1, 22, 'formula', 'open', 5, '2026-03-27 04:57:55'),
(174, 1, 22, 'key_concept', 'open', 5, '2026-03-27 04:58:06'),
(175, 1, 13, 'key_concept', 'open', 5, '2026-03-27 04:58:15'),
(176, 1, 19, 'key_concept', 'open', 5, '2026-03-27 04:58:37'),
(177, 1, 22, 'key_concept', 'open', 5, '2026-03-27 05:07:12'),
(178, 1, 22, 'flashcard', 'open', 5, '2026-03-27 05:07:24'),
(179, 1, 22, 'flashcard', 'view', 5, '2026-03-27 05:07:27'),
(180, 1, 22, 'flashcard', 'view', 5, '2026-03-27 05:07:30'),
(181, 1, 19, 'key_concept', 'open', 5, '2026-03-27 05:07:37'),
(182, 1, 13, 'key_concept', 'open', 5, '2026-03-27 05:07:48'),
(183, 1, 13, 'key_concept', 'open', 5, '2026-03-27 06:03:36'),
(184, 1, 19, 'flashcard', 'open', 5, '2026-03-27 06:04:40'),
(185, 1, 19, 'flashcard', 'view', 5, '2026-03-27 06:04:45'),
(186, 1, 19, 'flashcard', 'view', 5, '2026-03-27 06:04:47'),
(187, 1, 19, 'flashcard', 'view', 5, '2026-03-27 06:04:48'),
(188, 1, 19, 'flashcard', 'view', 5, '2026-03-27 06:04:57'),
(189, 1, 19, 'key_concept', 'open', 5, '2026-03-27 06:05:07'),
(190, 1, 23, 'formula', 'open', 5, '2026-03-27 06:05:24'),
(191, 1, 23, 'formula', 'open', 5, '2026-03-27 06:05:24');

-- --------------------------------------------------------

--
-- Table structure for table `subjects`
--

CREATE TABLE `subjects` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `subject_name` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subjects`
--

INSERT INTO `subjects` (`id`, `user_id`, `subject_name`, `created_at`) VALUES
(9, 2, 'Data structures', '2026-02-17 07:36:44'),
(13, 1, 'Design', '2026-03-10 10:25:42'),
(19, 1, 'App development', '2026-03-16 07:29:45'),
(21, 1, 'Algorithms', '2026-03-21 13:30:18'),
(22, 1, 'Physics', '2026-03-22 05:34:57'),
(23, 1, 'Computer science', '2026-03-23 10:38:51'),
(25, 3, 'Calculus', '2026-03-25 14:36:38'),
(26, 3, 'Chemistry', '2026-03-25 14:39:34');

-- --------------------------------------------------------

--
-- Table structure for table `subject_documents`
--

CREATE TABLE `subject_documents` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `subject_name` varchar(100) DEFAULT NULL,
  `document_path` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject_documents`
--

INSERT INTO `subject_documents` (`id`, `user_id`, `subject_name`, `document_path`, `created_at`) VALUES
(1, 2, 'Computer architecture', 'uploads/documents/Gurupadigam.pdf', '2026-03-12 06:00:38'),
(3, 1, 'Data science', 'uploads/documents/1773296202_document_1773296201972.pdf', '2026-03-12 06:16:42'),
(4, 1, 'Computer architecture', 'uploads/documents/1773646019_document_1773646018427.pdf', '2026-03-16 07:26:59'),
(7, 3, 'Calculus', 'uploads/documents/1774449431_document_1774449428650.pdf', '2026-03-25 14:37:11');

-- --------------------------------------------------------

--
-- Table structure for table `subject_images`
--

CREATE TABLE `subject_images` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `subject_name` varchar(100) NOT NULL,
  `image_path` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject_images`
--

INSERT INTO `subject_images` (`id`, `user_id`, `subject_name`, `image_path`, `created_at`) VALUES
(3, 1, 'Design', 'uploads/1774094954_upload_image.jpg', '2026-03-21 12:09:14'),
(4, 1, 'Data science', 'uploads/1774094975_upload_image.jpg', '2026-03-21 12:09:35'),
(5, 1, 'Data science', 'uploads/1774095152_upload_image.jpg', '2026-03-21 12:12:32'),
(7, 3, 'Calculus', 'uploads/1774449488_upload_image.jpg', '2026-03-25 14:38:08');

-- --------------------------------------------------------

--
-- Table structure for table `subject_urls`
--

CREATE TABLE `subject_urls` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `subject_name` varchar(255) DEFAULT NULL,
  `url_name` varchar(255) DEFAULT NULL,
  `url_link` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `subject_urls`
--

INSERT INTO `subject_urls` (`id`, `user_id`, `subject_name`, `url_name`, `url_link`, `created_at`) VALUES
(1, 1, 'Data Structures', 'Linked List Tutorial', 'https://youtube.com/watch?v=abc123', '2026-03-10 11:12:43'),
(2, 1, 'Computer science', 'linked list', 'https://youtu.be/R9PTBwOzceo?si=Bf_tJqqAN1jIC17A', '2026-03-10 11:37:30'),
(3, 1, 'Design', 'Designers', 'reference', '2026-03-12 06:11:53'),
(4, 1, 'App development', 'youtube guide for app dev', 'http://app', '2026-03-16 07:30:37'),
(5, 3, 'Calculus', 'arms', 'www.saveetha.arms', '2026-03-25 14:39:07');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `signup`
--
ALTER TABLE `signup`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `student_activity`
--
ALTER TABLE `student_activity`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subjects`
--
ALTER TABLE `subjects`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subject_documents`
--
ALTER TABLE `subject_documents`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `subject_images`
--
ALTER TABLE `subject_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `subject_urls`
--
ALTER TABLE `subject_urls`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT for table `signup`
--
ALTER TABLE `signup`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `student_activity`
--
ALTER TABLE `student_activity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=192;

--
-- AUTO_INCREMENT for table `subjects`
--
ALTER TABLE `subjects`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT for table `subject_documents`
--
ALTER TABLE `subject_documents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `subject_images`
--
ALTER TABLE `subject_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `subject_urls`
--
ALTER TABLE `subject_urls`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `signup` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `subject_images`
--
ALTER TABLE `subject_images`
  ADD CONSTRAINT `subject_images_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `signup` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
