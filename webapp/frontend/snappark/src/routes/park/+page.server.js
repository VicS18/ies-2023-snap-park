
console.log("Y");

/** @type {import('./$types').Actions} */
export const actions = {
    default: async (event) => {
        // AH, do nothing :)
    },
	create: async ({ cookies, request }) => {
        console.log("CALLED");
		// TODO: call REST API end point: http://localhost:9090/api/v1/park/John.
        // TODO: Refactor to take in username of requester.

        const formData = await request.formData();
        const formJson = Object.fromEntries(formData.entries());

        console.log("FORM JSON: ");
        console.log(formJson);
        console.log("\n");

        const response = await fetch('http://localhost:9090/api/v1/park/John', {
            method: 'POST',
            body: JSON.stringify(formJson),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        console.log("RESPONSE: ");
        console.log(response);

        return { success: true };
	}
};
