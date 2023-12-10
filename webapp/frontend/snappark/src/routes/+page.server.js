
console.log("Y");

/** @type {import('./$types').Actions} */
export const actions = {
	create: async ({ request }) => {
        console.log("CALLED");
        // TODO: Refactor to take in username of requester.

        console.log("REQUEST: ");
        console.log(request);

        const formData = await request.formData();
        console.log("FORM DATA: ");
        console.log(...formData);
        console.log(Object.fromEntries(formData.entries()));
        const formJson = Object.fromEntries(formData.entries());

        console.log("FORM JSON: ");
        console.log(formJson);
        console.log("\n");
        
        const response = await fetch('http://app:9090/api/v1/parks/manager/John', {
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
