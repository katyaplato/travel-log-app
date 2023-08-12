const axios = require("axios");
const { useState } = require("react");
require("../components/registation.css");

function Register()
{
    const [id, setId] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    // const [city, setCity] = useState("");
    // const [phone, setPhone] = useState("");
    // const [salary, setSalary] = useState("");

    async function handleSubmit(event)
    {
        event.preventDefault();
        try
        {
            await axios.post("http://localhost:8080/api/auth/register",
                {
                    email: email,
                    username: username
                    // fname: firstname,
                    //
                    // lname : lastname,
                    // city : city,
                    // phone : phone,
                    // salary :salary
                });
            alert("User Registation Successfully");
            // setId("");
            // setFname("");
            // setLname("");
            // setCity("");
            // setPhone("");
            // setSalary("");
            setEmail("");
            setUsername("");

        }
        catch(err)
        {
            alert("User Registation Failed");
        }
    }
    return (
        <div className="register-container">

            <form className="register-form" onSubmit={handleSubmit}>
                <br></br>
                <h1>Register</h1>
                <p>Fill in the Information Below</p>

                {/*<input type="text"*/}
                {/*       name="id"*/}
                {/*       placeholder="id"*/}

                {/*       onChange={(event) =>*/}
                {/*       {*/}
                {/*           setId(event.target.value);*/}
                {/*       }}*/}
                {/*/>*/}

                <input type="text"
                       name="email"
                       placeholder="E-mail"
                       onChange={(event) =>
                       {
                           setEmail(event.target.value);
                       }}
                />

                <input type="text"
                       name="username"
                       placeholder="lastname"
                       onChange={(event) =>
                       {
                           setUsername(event.target.value);
                       }}
                />


                {/*<input type="text"*/}
                {/*       name="city"*/}
                {/*       placeholder="city"*/}
                {/*       onChange={(event) =>*/}
                {/*       {*/}
                {/*           setCity(event.target.value);*/}
                {/*       }}*/}
                {/*/>*/}
                {/*<input type="text"*/}
                {/*       name="phone"*/}
                {/*       placeholder="phone"*/}
                {/*       onChange={(event) =>*/}
                {/*       {*/}
                {/*           setPhone(event.target.value);*/}
                {/*       }}*/}
                {/*/>*/}

                {/*<input type="text"*/}
                {/*       name="salary"*/}
                {/*       placeholder="salary"*/}
                {/*       onChange={(event) =>*/}
                {/*       {*/}
                {/*           setSalary(event.target.value);*/}
                {/*       }}*/}
                {/*/>*/}

                <button type="submit">Register</button>


            </form>


        </div>
    )
}

module.exports = Register;