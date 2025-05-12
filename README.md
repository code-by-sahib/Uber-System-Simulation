# Table of Contents
- [RustyShield: Password Strength Tester](#rustyshield-password-strength-tester)
- [Features](#features)
- [Requirements](#requirements)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)
- [Acknowledgements](#acknowledgements)
- [Contact](#contact)
---
# RustyShield: Password Strength Tester
A simple CLI tool written in Rust to evaluate password strength based on length, lowercase, uppercas
e, digits, and special symbols.
![GitHub](https://img.shields.io/github/license/code-by-sahib/RustyShield-Password-Strength-Tester)
## Features
- Checks password length (≥ 8 characters)
- Detects presence of lowercase letters
- Detects presence of uppercase letters
- Detects presence of digits
- Detects presence of special symbols
- Classifies strength as Weak, Moderate, or Strong
- Provides improvement suggestions for non-strong passwords
## Requirements
- Rust (1.56+)
- Cargo
- `regex` crate (v1)
- `colored` crate (v2)
## Installation
1. **Clone the repository**
 ```bash
 git clone https://github.com/code-by-sahib/RustyShield-Password-Strength-Tester.git
 ```
2. **Enter the project directory**
 ```bash
 cd RustyShield-Password-Strength-Tester
 ```
3. **Build the project**
 ```bash
 cargo build --release
 ```
## Usage
1. **Run the application**
 ```bash
 cargo run
 ```
2. **Follow the prompt** to enter your password.
Example:
```text
RustyShield: Password Strength Tester
Enter a password to check its strength:
> P@ssw0rd123
Password strength: Strong (Score: 5/5)
```
## Contributing
Contributions are welcome! Please:
- Fork this repository
- Open an issue to report bugs or suggest features
- Submit a pull request with enhancements or fixes
## License
MIT License
Copyright (c) 2025 Sahib Ahluwalia
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and as
sociated documentation files (the "Software"), to deal in the Software without restriction, includin
g without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subj
ect to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,T LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE A. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMALITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR H THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
## Acknowledgements
- Built as a personal Rust learning project.
- Inspired by common password-strength checkers and security best practices.
## Contact
For questions or feedback, please reach out via e-mail: sahib.ahluwalia@torontomu.ca
