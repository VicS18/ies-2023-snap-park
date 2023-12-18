/** @type {import('./$types').PageLoad} */
export async function load({ params }) {
    // TODO: Refactor to validate requester as a manager of this park and deny if not a manager

    // Fetch park info - VULNERABLE !!!

    // TODO: Refactor so only one API request is done instead of several

    // NOTE: Javascript doesn't like null values when parsing JSON

    const parkGeneral = await fetch('http://app:9090/api/v1/parks/' + params.parkId);

    const avgLight = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/avgLight');

    const sensorCount = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/sensorCount');

    const currDate = new Date().getTime()

    const nwoccupancies = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/occupancies/' + (currDate - (86400000 * 7)) + '/' + currDate + '/' + 64);
    const ndoccupancies = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/occupancies/' + (currDate - (86400000 * 1)) + '/' + currDate + '/' + 64);
    const nmoccupancies = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/occupancies/' + (currDate - (86400000 * 30)) + '/' + currDate + '/' + 64);

    const annualRevenue = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/revenue/annual');
    const monthlyRevenue = await fetch('http://app:9090/api/v1/parks/' + params.parkId + '/revenue/monthly');

    return {
        park: parkGeneral.json(),
        avgLight: avgLight.json(),
        sensorCount: sensorCount.json(),
        annualRevenue: annualRevenue.json(),
        monthlyRevenue: monthlyRevenue.json(),
        woccupancies: nwoccupancies.json(),
        doccupancies: ndoccupancies.json(),
        moccupancies: nmoccupancies.json()

    };
}