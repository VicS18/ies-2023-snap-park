
/** @type {import('./$types').Actions} */
export const actions = {
    createSensor: async({ request, params }) => {
        // TODO: Refactor to take in username of requester.

        const formData = await request.formData();

        const formJson = Object.fromEntries(formData.entries());

        // TODO: Migrate REST API address to the lib directory
        const response = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/sensors', {
            method: 'POST',
            body: JSON.stringify(formJson),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        return { success: true };
    }
};

/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
    // TODO: Refactor to validate requester as a manager of this park and deny if not a manager

    // Fetch park info - VULNERABLE !!!

    // TODO: Refactor so only one API request is done instead of several

    // NOTE: Javascript doesn't like null values when parsing JSON

    const parkGeneral = await fetch('http://app:9090/api/v1/parks/' + params.parkId);

    const avgLight = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/avgLight');

    const sensorCount = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/sensorCount');

    const annualRevenue = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/revenue/annual');

    const monthlyRevenue = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/revenue/monthly');

	return {
        park: parkGeneral.json(),
        avgLight: avgLight.json(),
        sensorCount: sensorCount.json(),
        annualRevenue: annualRevenue.json(),
        monthlyRevenue: monthlyRevenue.json()
	};
}